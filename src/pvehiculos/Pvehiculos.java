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
    
}
