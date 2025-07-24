package controlador;

import modelo.BackupDatos;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Ctrl_Backup {
    private static final String BACKUP_DIR = "C:\\respaldos";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Vacío si no hay contraseña
    private static final String DB_NAME = "carpinteriasistema";
    private static final String MYSQLDUMP_PATH = "C:/xampp/mysql/bin/mysqldump.exe";
    private static final String MYSQL_PATH = "C:/xampp/mysql/bin/mysql.exe";

    public List<BackupDatos> obtenerBackups() {
        List<BackupDatos> backups = new ArrayList<>();
        File dir = new File(BACKUP_DIR);
        System.out.println("Buscando backups en: " + dir.getAbsolutePath() + " a las " + new Date());

        if (!dir.exists()) {
            System.out.println("Directorio no existe, creándolo...");
            if (dir.mkdirs()) {
                System.out.println("Directorio creado.");
            } else {
                System.out.println("Error al crear el directorio.");
                return backups;
            }
        }

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".sql"));
        if (files == null || files.length == 0) {
            System.out.println("No se encontraron archivos .sql.");
            return backups;
        }

        for (File file : files) {
            BackupDatos backup = new BackupDatos();
            backup.setFilename(file.getName());
            backup.setDatabaseName(file.getName().replace(".sql", ""));
            backup.setCreationDate(new Date(file.lastModified()));
            backup.setSize(file.length());
            backups.add(backup);
            System.out.println("Backup encontrado: " + file.getName());
        }
        return backups;
    }

    public boolean crearBackup(String backupName) {
    try {
        if (backupName == null || backupName.trim().isEmpty()) {
            System.err.println("Error: El nombre del backup no puede estar vacío.");
            return false;
        }

        if (backupName.matches(".*[<>:\"/\\\\|?*].*")) {
            System.err.println("Error: El nombre contiene caracteres no permitidos.");
            return false;
        }

        String filename = backupName.endsWith(".sql") ? backupName : backupName + ".sql";
        String backupPath = BACKUP_DIR + File.separator + filename;
        File backupFile = new File(backupPath);

        if (backupFile.exists()) {
            System.err.println("Error: El archivo de backup ya existe: " + filename);
            return false;
        }

        File dir = new File(BACKUP_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Error: No se pudo crear el directorio: " + BACKUP_DIR);
            return false;
        }

        String passwordParam = DB_PASSWORD.isEmpty() ? "--password=" : "-p" + DB_PASSWORD;
        String[] command = new String[]{
            MYSQLDUMP_PATH,
            "--hex-blob",                             // importante para imágenes (BLOB)
            "--default-character-set=utf8",           // para evitar problemas de codificación
            "-u" + DB_USER,
            passwordParam,
            DB_NAME,
            "-r", backupPath
        };

        System.out.println("Ejecutando comando: " + String.join(" ", command));
        Process process = Runtime.getRuntime().exec(command);

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errorOutput = new StringBuilder();
        String line;
        while ((line = errorReader.readLine()) != null) {
            errorOutput.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Backup creado exitosamente: " + filename);
            return true;
        } else {
            System.err.println("Error al crear backup, código: " + exitCode);
            System.err.println("Salida de error: " + errorOutput.toString());
            return false;
        }

    } catch (Exception e) {
        System.err.println("Error al crear backup: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}


    public boolean eliminarBackup(String filename) {
        File file = new File(BACKUP_DIR + File.separator + filename);
        return file.exists() && file.delete();
    }

    public boolean restaurarBackup(String filename) {
        try {
            String backupPath = BACKUP_DIR + File.separator + filename;
            File backupFile = new File(backupPath);
            if (!backupFile.exists()) {
                System.out.println("El archivo de backup no existe: " + backupPath);
                return false;
            }

            String passwordParam = DB_PASSWORD.isEmpty() ? "--password=" : "-p" + DB_PASSWORD;
            String[] command = new String[]{
                MYSQL_PATH, "-u" + DB_USER, passwordParam, DB_NAME
            };

            System.out.println("Ejecutando comando de restauración...");
            Process process = Runtime.getRuntime().exec(command);

            // Enviar contenido del archivo .sql al proceso
            try (BufferedReader fileReader = new BufferedReader(new FileReader(backupFile));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.flush();
            }

            // Hilo para manejar la salida del proceso
            Thread processThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("Salida: " + line);
                    }
                    while ((line = errorReader.readLine()) != null) {
                        System.err.println("Error: " + line);
                    }
                    int exitCode = process.waitFor();
                    SwingUtilities.invokeLater(() -> {
                        if (exitCode == 0) {
                            System.out.println("Backup restaurado exitosamente: " + filename);
                        } else {
                            System.err.println("Error al restaurar backup, código: " + exitCode);
                        }
                    });
                } catch (Exception e) {
                    System.err.println("Error al esperar el proceso: " + e.getMessage());
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> System.err.println("Error en el proceso: " + e.getMessage()));
                }
            });
            processThread.start();

            return true;
        } catch (Exception e) {
            System.err.println("Error al restaurar backup: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean exportarBackup(String filename) {
        try {
            String sourcePath = BACKUP_DIR + File.separator + filename;
            File sourceFile = new File(sourcePath);
            if (!sourceFile.exists()) {
                System.err.println("El archivo de backup no existe: " + sourcePath);
                return false;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Exportar Respaldo");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File(filename));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos SQL", "sql"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                System.out.println("Exportación cancelada por el usuario.");
                return false;
            }

            File destinationFile = fileChooser.getSelectedFile();
            if (!destinationFile.getName().toLowerCase().endsWith(".sql")) {
                destinationFile = new File(destinationFile.getAbsolutePath() + ".sql");
            }

            if (destinationFile.exists()) {
                System.err.println("El archivo de destino ya existe: " + destinationFile.getAbsolutePath());
                return false;
            }

            try (FileInputStream fis = new FileInputStream(sourceFile);
                 FileOutputStream fos = new FileOutputStream(destinationFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println("Backup exportado exitosamente a: " + destinationFile.getAbsolutePath());
                return true;
            }

        } catch (IOException e) {
            System.err.println("Error al exportar backup: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean restaurarBackupDesdeArchivo(File archivo) {
    try {
        if (!archivo.exists()) {
            System.err.println("El archivo seleccionado no existe: " + archivo.getAbsolutePath());
            return false;
        }

        String passwordParam = DB_PASSWORD.isEmpty() ? "--password=" : "-p" + DB_PASSWORD;
        String[] command = new String[]{
            MYSQL_PATH, "-u" + DB_USER, passwordParam, DB_NAME
        };

        System.out.println("Restaurando desde archivo externo: " + archivo.getAbsolutePath());
        Process process = Runtime.getRuntime().exec(command);

        try (BufferedReader fileReader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {

            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }

        // Manejo de salida y errores del proceso
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Salida: " + line);
        }
        while ((line = errorReader.readLine()) != null) {
            System.err.println("Error: " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Backup restaurado exitosamente desde archivo externo.");
            return true;
        } else {
            System.err.println("Error al restaurar backup externo, código: " + exitCode);
            return false;
        }

    } catch (Exception e) {
        System.err.println("Error al restaurar desde archivo externo: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

}
