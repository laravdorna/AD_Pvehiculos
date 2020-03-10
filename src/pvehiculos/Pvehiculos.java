/* EJERCICIO PRUEBA DE EXAMEN
 * PASOS:
 0º insertar los jar de la carpeta examen son 3 uno por cada db! si no no funciona 
 /home/oracle/drivers/examen
 1º MONGO:
 iniciar mongo:
 -SERVER: mondod
 -CLIENT:mongo
 crear colección vendas dado el ficheiro
 2º Crear clases java vehiculos y clientes
 3ªDB obj_db vehicli con dos clases vehiculos y clientes.
 abrir db para comprobar datos:
 /home/oracle/objectdb-2.7.5_01/bin y ejecutar en terminal explorer.sh
 !OJO NO DEJAR ABIERTA LA DB!, FILE CLOSE CONNECTION!! no cierres el programa a lo loco que no cierra la conexion
 4ºORACLE:
 COMANDOS
 lanzar servidor oracle para trabajar desde java con el listener

 . oraenv
 orcl
 rlwrap sqlplus sys/oracle as sysdba 
 startup
 conn hr/hr
 exit
 lsnrctl start
 lsnrctl status

 CREAR LA TABLA DESDE EL SCRIPT DADO:  @ creafinalveh.sql


 */
package pvehiculos;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.bson.Document;

/**
 *
 * @author oracle
 */
public class Pvehiculos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        //abrir conexion 
        conexionM();
        getConexionO();
        //1º USAR UN ITERABLE, por que se van a retornar varios resultados de MONGO
        FindIterable<Document> datosMongo = collection.find();

        //recorrer la dbMongo para obtener los datos
        for (Document documento : datosMongo) {
            System.out.println(documento);
            double id = documento.getDouble("_id");
            String dni = documento.getString("dni");
            String codveh = documento.getString("codveh");

            //OBJECTDB
            EntityManager em = emf.createEntityManager();

            //PRIMERA BUSQUEDA EN CLIENTES
            //abir conexion segura odb
            em.getTransaction().begin();

            TypedQuery<Clientes> queryClientes = em.createQuery(
                    "SELECT c FROM Clientes c WHERE dni= :dni", Clientes.class);
            //sabiendo que el dni es el cogido de mongo
            queryClientes.setParameter("dni", dni);

            Clientes cliente = queryClientes.getSingleResult();
            System.out.println(cliente.toString());
            String nomec = cliente.getNomec();
            boolean descuento = cliente.getNcompras() > 0;

            //cerrar conexion obd hacer commit
            em.getTransaction().commit();

            //SEGUNDA BUSQUEDA EN VEHICULOS
            //abir conexion segura odb
            em.getTransaction().begin();

            TypedQuery<Vehiculos> queryVehiculos = em.createQuery(
                    "SELECT v FROM Vehiculos v WHERE codveh= :codveh", Vehiculos.class);
            queryVehiculos.setParameter("codveh", codveh);

            Vehiculos vehiculo = queryVehiculos.getSingleResult();
            System.out.println(vehiculo.toString());
            String nonveh = vehiculo.getNomveh();
            long prezoOrixe = vehiculo.getPrezoorixe();
            int anoMatricula = vehiculo.getAnomatricula();

            long prezoFinal = prezoOrixe - ((2019 - anoMatricula) * 500) - (descuento ? 500 : 0);
            //cerrar conexion obd hacer commit
            em.getTransaction().commit();

            //GUARDAR EN ORACLE EL RESULTADO FINAL 
            PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO finalveh values(?,?,?,TIPO_VEHF(?,?))"
            );
            ps.setDouble(1, id);
            ps.setString(2, dni);
            ps.setString(3, nomec);
            ps.setString(4, nonveh);
            ps.setLong(5, prezoFinal);
            ps.executeUpdate();

        }
        //cerrar conexion 
        closeM();
        closeConexionO();
        emf.close();
    }

    //METODOS DE CONEXIÓN ORACLE
    public static Connection conexion = null;

    public static Connection getConexionO() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;

        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }

    public static void closeConexionO() throws SQLException {
        conexion.close();
    }

    //METODOS DE CONEXIÓN MONGO
    public static MongoClient mongoClient = null;
    public static MongoDatabase database = null;
    public static MongoCollection<Document> collection = null;

    public static void conexionM() {
        String ip = "localhost";
        int puerto = 27017;
        String db = "test";
        String cll = "vendas";
        mongoClient = new MongoClient(ip, puerto);
        database = mongoClient.getDatabase(db);
        collection = database.getCollection(cll);
    }

    public static void closeM() {
        mongoClient.close();
    }

    //METODOS DE CONEXION ODB
    public static EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("/home/oracle/Desktop/compartido/Pvehiculos/vehicli.odb");

}
