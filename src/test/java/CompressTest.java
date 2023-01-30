import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class CompressTest {
    public static void main(String[] args) throws IOException {
        byte[] bytes = {2,3,0,9,-64};
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(9);
        DeflaterOutputStream d = new DeflaterOutputStream(b, deflater);
        d.write(bytes);
        d.finish();
        d.close();

        System.out.println(Arrays.toString(b.toByteArray()));
    }
}
