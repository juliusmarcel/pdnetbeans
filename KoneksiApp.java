package dokumen;
import java.sql.Connection;
import java.sql.DriverManager;

class Koneksi{
    Connection con;
    public Connection getConnect(){
        String url = "jdbc:mysql://localhost:3306/rekam_medis";
        String user = "root";
        String pass = "";
        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi berhasil");
        } catch (Exception e) {
            System.out.println("Koneksi Gagal");
        }
        
        return con;
    }
}
public class KoneksiApp {
    public static void main(String[] args) {
        new Koneksi().getConnect();
    }
}