package modelo;

import java.util.Date;

public class BackupDatos {
    private String filename; // Nombre del archivo de respaldo (e.g., "backup_2025-07-21.sql")
    private String databaseName; // Nombre de la base de datos
    private Date creationDate; // Fecha de creación del respaldo
    private long size; // Tamaño del archivo en bytes

    public BackupDatos(String filename, String databaseName, Date creationDate, long size) {
        this.filename = filename;
        this.databaseName = databaseName;
        this.creationDate = creationDate;
        this.size = size;
    }

    // Getters y setters
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public String getDatabaseName() { return databaseName; }
    public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }

    // Formatear tamaño para mostrar (e.g., KB, MB)
    public String getFormattedSize() {
        if (size < 1024) return size + " B";
        else if (size < 1024 * 1024) return String.format("%.2f KB", size / 1024.0);
        else return String.format("%.2f MB", size / (1024.0 * 1024));
    }

    public BackupDatos() {
    }
    
    
    
}