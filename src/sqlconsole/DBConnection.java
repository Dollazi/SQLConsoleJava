package sqlconsole;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
	private static Connection connection = null;

	public static void connect() {
		try {
			// db parameters
			File temp = new File("sqliteDB.db");
			String url = "jdbc:sqlite:" + temp.getAbsolutePath().replace("\\", "\\\\");
			// create a connection to the database
			DBConnection.connection = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static List<List<String>> executeSQL(String sqlCommand) {
		// Lista (linhas) de listas (colunas) para armazenar os dados
		// resultantes de um comando SQL tipo SELECT
		List<List<String>> tableData = null;
		try (Statement statement = DBConnection.connection.createStatement()) {
			try (ResultSet rs = statement.executeQuery(sqlCommand)) {

				ResultSetMetaData rsmd = rs.getMetaData();
				int nColumns = rsmd.getColumnCount();
				List<String> headers = new ArrayList<String>();
				for (int i = 1; i <= nColumns; i++) {
					headers.add(rsmd.getColumnLabel(i));
				}
				tableData = new ArrayList<List<String>>();
				// Adicionar os cabeçalhos das colunas
				tableData.add(headers);
				while (rs.next()) {
					// Criar um vetor para uma linha do resultado
					List<String> row = new ArrayList<String>();
					// Preenchê-lo com os valores de cada coluna
					for (int i = 1; i <= nColumns; i++) {
						row.add(rs.getString(i));
					}
					// Adiciona esta linha ao resultado
					tableData.add(row);
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro de SQL: " + e);
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
		return tableData;
	}

	public static int updateSQL(String sqlCommand) {
		int result = 0;
		try {
			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sqlCommand);
			statement.close();
		} catch (SQLException e) {
			System.out.println("Erro de SQL: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return result;
	}
}