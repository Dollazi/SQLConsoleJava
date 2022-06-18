package sqlconsole;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		// Estabelecer uma conexão com o banco de dados
		DBConnection.connect();
		// EXEMPLOS
		// Destruir a tabela se ela existir
		DBConnection.updateSQL(
			"DROP TABLE IF EXISTS contacts"
		);
		// Recriar uma tabela
		DBConnection.updateSQL(
			"CREATE TABLE IF NOT EXISTS contacts ( " + 
			 "    contact_id INTEGER PRIMARY KEY," +
			 "    first_name TEXT NOT NULL, " +
			 "    last_name TEXT NOT NULL, " +
			 "    email text NOT NULL UNIQUE, " +
			 "    phone text NOT NULL UNIQUE " +
			");"
		);
		// Inserir algumas linhas
		DBConnection.updateSQL("INSERT INTO contacts VALUES(1, 'Marco', 'Furlan', 'marco.furlan@maua.br', '4239-3000' )");
		// Consulta
		List<List<String>> rows = DBConnection.executeSQL("SELECT * FROM contacts");
		if(rows != null) {
			for(List<String> row : rows) {
				System.out.print("|");
				for(String col : row) {
					System.out.print(col);
					System.out.print("|");
				}
				System.out.println();
			}
		}
	}
}
