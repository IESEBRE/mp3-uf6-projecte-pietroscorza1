package org.example.model.impls;

import org.example.model.daos.DAO;
import org.example.model.entities.Client;
import org.example.model.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que implementa la interfície DAO per a la gestió de clients
 */
public class ClientDAOJDBCOracleImpl implements DAO<Client> {

    PropertiesFitxer conexio = new PropertiesFitxer();

    @Override
    /**
     * Mètode que retorna un client a partir del seu nom
     */
    public Client get(String nom) throws DAOException {
        // Declaración de variables del método

       Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Client cliente = null;

        // Acceso a la BD usando el API JDBC
        try {

            con = DriverManager.getConnection(
                    conexio.getUrl(),
                    conexio.getUsername(),
                    conexio.getPassword()
            );
            st = con.prepareStatement("SELECT * FROM CLIENTES WHERE NOM = ?");
            st.setString(1, nom);
            rs = st.executeQuery();

            if (rs.next()) {
                cliente = new Client(rs.getString("NOM"), rs.getDouble("INGRES"), rs.getInt("EDAD"), rs.getBoolean("ACTIU"), rs.getString("GENERE"));
            }
        } catch (SQLException throwables) {
            throw new DAOException(1);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new DAOException(1);
            }
        }
        return cliente;
    }


    @Override
    /**
     * Mètode que retorna tots els clients de la BD
     */
    public List<Client> getAll() throws DAOException {
        //Declaració de variables del mètode
        List<Client> estudiants = new ArrayList<>();

        //Accés a la BD usant l'API JDBC
        try (Connection con = DriverManager.getConnection(
                conexio.getUrl(),
                conexio.getUsername(),
                conexio.getPassword()
        );
             PreparedStatement st = con.prepareStatement("SELECT * FROM CLIENTES");
             ResultSet rs = st.executeQuery();
        ) {

            while (rs.next()) {
                estudiants.add(new Client(rs.getString("NOM"),rs.getDouble("INGRES"), rs.getInt("EDAD"), rs.getBoolean("ACTIU"), rs.getString("GENERE")));
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            //System.out.println(tipoError+" "+throwables.getMessage());
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    System.out.println(throwables.getMessage());;  //error desconegut
            }
            throw new DAOException(20);
        }


        return estudiants;
    }

    @Override
    /**
     * Mètode que guarda els canvis fets a la BD
     */
    public void save() throws DAOException {
//commit
      String sql = "commit";
        try (Connection con = DriverManager.getConnection(
                conexio.getUrl(),
                conexio.getUsername(),
                conexio.getPassword()
        );
             PreparedStatement st = con.prepareStatement(sql);
        ) {
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(16);
        }
    }


    @Override
    /**
     * Mètode que actualitza un client de la BD
     */
    public void update(Client obj, String nomAntic) throws DAOException {

        String sql = "UPDATE CLIENTES SET INGRES = ?, EDAD = ?, ACTIU = ?, NOM = ?, GENERE = ? WHERE NOM = ?";
        try (Connection con = DriverManager.getConnection(
                conexio.getUrl(),
                conexio.getUsername(),
                conexio.getPassword()
        );
             PreparedStatement st = con.prepareStatement(sql);
        ) {
            st.setDouble(1, obj.getIngres());
            st.setInt(2, obj.getEdad());
            st.setBoolean(3, obj.getMatriculat());
            st.setString(4, obj.getNom());
            st.setString(5, obj.getGenere());
            st.setString(6, nomAntic);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(16);
        }
    }

    @Override
    /**
     * Mètode que elimina un client de la BD
     */
    public void delete(Client obj) throws DAOException {
        String sql = "DELETE FROM CLIENTES WHERE NOM = ?";
        try (Connection con = DriverManager.getConnection(
                conexio.getUrl(),
                conexio.getUsername(),
                conexio.getPassword()
        );
             PreparedStatement st = con.prepareStatement(sql);
        ) {
            st.setString(1, obj.getNom());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(14);
        }
    }


    @Override
    /**
     *  Mètode que insereix un client a la BD
     */
    public void insert(Client obj) throws DAOException {
        String sql = "INSERT INTO CLIENTES (NOM, INGRES, EDAD, ACTIU, GENERE) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(
                conexio.getUrl(),
                conexio.getUsername(),
                conexio.getPassword()
        );
             PreparedStatement st = con.prepareStatement(sql);
        ) {
            st.setString(1, obj.getNom());
            st.setDouble(2, obj.getIngres());
            st.setInt(3, obj.getEdad());
            st.setBoolean(4, obj.getMatriculat());
            st.setString(5, obj.getGenere());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(12);

    }
}
@Override
/**
 * Mètode que crea la taula CLIENTES a la BD
 */
    public void crearTabla() throws DAOException {
        String sql = "BEGIN TAULA; END;";
        try (Connection con = DriverManager.getConnection(
                conexio.getUrl(),
                conexio.getUsername(),
                conexio.getPassword()
        );
             PreparedStatement st = con.prepareStatement(sql);
        ) {
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(10);
        }
    }
}
