package com.sermar.zara.orderservice.dao;

import com.sermar.zara.orderservice.dto.Order;

import java.sql.*;
import java.util.Hashtable;

/**
 * Mejorar cada uno de los métodos a nivel SQL y código cuando sea necesario
 * Razonar cada una de las mejoras que se han implementado
 * No es necesario que el código implementado funcione 
 */
public class TestSqlDaoUpgrade {

	private static TestSqlDaoUpgrade instance = new TestSqlDaoUpgrade();
	private Hashtable<Long, Long> maxOrderUser;
	
	private TestSqlDaoUpgrade() {

	}

	public static TestSqlDaoUpgrade getInstance() {

		return instance;
	}

	/**
	 * Obtiene el ID del último pedido para cada usuario
	 */
	public Hashtable<Long, Long> getMaxUserOrderId(long idTienda) throws Exception {

		//Se modifica la SQL para obtener el ID del usuario y el ID del pedido máximo asociado a ese usuario.
		String query = "SELECT max(ID_PEDIDO), ID_USUARIO FROM PEDIDOS WHERE ID_TIENDA = ? GROUP BY ID_USUARIO";
		PreparedStatement stmt = getConnection().prepareStatement(query);
		stmt.setLong(1, idTienda); //Se recomienda usar parámetros de PreparedStatement para evitar ataques de inyección SQL.
		ResultSet rs = stmt.executeQuery();
		maxOrderUser = new Hashtable<Long, Long>();
		
		while(rs.next()) {

			long idPedido = rs.getInt("ID_PEDIDO");
			long idUsuario = rs.getInt("ID_USUARIO");

			//Se borra la condición que estaba intentando coger el pedido máximo que ya no es necesario gracias a SQL.
			maxOrderUser.put(idUsuario, idPedido);

		}

		return maxOrderUser;
	}

	/**
	 * Copia todos los pedidos de un usuario a otro
	 */
	public void copyUserOrders(long idUserOri, long idUserDes) throws Exception {

		/**
		 * Añado por el parametro idUserDes que no tengo claro cuál es el objetivo
		 * pero tengo entendido que prefiero los pedidos de un usuario a otro usuario diferente.
		 * Además, modifico el SQL, se puede insertar mediante un SELECT así se copia los mismos datos
		 * para diferente usuario.
		 */
		String query = "INSERT INTO pedidos (FECHA, TOTAL, SUBTOTAL, DIRECCION, ID_USUARIO) " +
				"SELECT FECHA, TOTAL, SUBTOTAL, DIRECCION, ? " +
				"FROM pedidos WHERE ID_USUARIO = ?";
		PreparedStatement stmt = getConnection().prepareStatement(query);
		stmt.setLong(1, idUserDes);
		stmt.setLong(2, idUserOri); //Se recomienda usar parámetros de PreparedStatement para evitar ataques de inyección SQL.

		stmt.executeUpdate();
	}

	/**
	 * Obtiene los datos del usuario y pedido con el pedido de mayor importe para la tienda dada
	 * Como es un método para obtener un objeto, no es correcto mandar un parametro y devolver así mismo
	 * para una buena práctica, es mejor devolver un objeto
	 */
	public Order getUserMaxOrder(long idTienda) throws Exception {

		//Modifico SQL porque devolvemos los datos del usuario y pedido pero que el pedido sea mayor importe para la tienda.
		String query = "SELECT U.ID_USUARIO, P.ID_PEDIDO, max(P.TOTAL), U.NOMBRE, U.DIRECCION FROM PEDIDOS AS P " +
				"INNER JOIN USUARIOS AS U ON P.ID_USUARIO = U.ID_USUARIO WHERE P.ID_TIENDA = ?";
		Connection connection = getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setLong(1, idTienda);//Se recomienda usar parámetros de PreparedStatement para evitar ataques de inyección SQL.
		ResultSet rs = stmt.executeQuery();

		//Cambio while a if porque sabemos ese select solo devuelve una fila
		if(rs.next()) {
			//Quito la condición porque he añadido TOTAL > 0 para que devuelva los datos con su total sea mayor que 0.
			//Añado return que devuelva el objeto
			return new Order()
					.setIdOrder(rs.getLong("ID_PEDIDO"))
					.setIdUser(rs.getLong("ID_USUARIO"))
					.setAddress(rs.getString("DIRECCION"))
					.setName(rs.getString("NOMBRE"))
					.setTotal(rs.getDouble("TOTAL"));

		} else {
			return null; //O también puede usar "throw new" (para devolver un mensaje de aviso que no hay datos)
		}
	}

	private Connection getConnection() throws SQLException {

		return DriverManager.getConnection("url", "usuario", "pass");
	}
}
