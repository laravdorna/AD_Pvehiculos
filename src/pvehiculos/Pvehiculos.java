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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bson.Document;

/**
 *
 * @author oracle
 */
public class Pvehiculos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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

    public static void closeConexion() throws SQLException {
        conexion.close();
    }

    //METODOS DE CONEXIÓN MONGO
    public static MongoClient mongoClient = null;
    public static MongoDatabase database = null;
    public static MongoCollection<Document> collection = null;

    public static void conexionM() {
        String ip = "localhost";
        int puerto = 27017;
        String db = "tenda";
        String cll = "pedidos";
        mongoClient = new MongoClient(ip, puerto);
        database = mongoClient.getDatabase(db);
        collection = database.getCollection(cll);
    }
     public static void closeM() {
           mongoClient.close();
     }
     
     
     
     //METODOS DE CONEXION ODB
     
     
}
