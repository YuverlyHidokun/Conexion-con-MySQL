import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudExample {

    private static final String URL = "jdbc:mysql://localhost:3306/Conector";
    private static final String USER = "root";
    private static final String PASSWORD = "Hidokun2003.y";

    public static void main(String[] args) {
        // Crear un registro
        createRecord("Yuverly Verdezoto", 20);

        // Leer todos los registros
        readRecords();

        // Actualizar un registro
        updateRecord(1, "Lady Marin", 19);

        // Leer todos los registros después de la actualización
        readRecords();

        // Eliminar un registro
        deleteRecord(1);

        // Leer todos los registros después de la eliminación
        readRecords();
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void createRecord(String name, int age) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO usuario (nombre, edad) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.executeUpdate();
                System.out.println("Registro creado con éxito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readRecords() {
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM usuario";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("nombre");
                        int age = resultSet.getInt("edad");
                        System.out.println("ID: " + id + ", Nombre: " + name + ", Edad: " + age);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateRecord(int id, String newName, int newAge) {
        try (Connection connection = getConnection()) {
            String updateQuery = "UPDATE usuario SET nombre = ?, edad = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setInt(2, newAge);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
                System.out.println("Registro actualizado con éxito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteRecord(int id) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM usuario WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Registro eliminado con éxito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
