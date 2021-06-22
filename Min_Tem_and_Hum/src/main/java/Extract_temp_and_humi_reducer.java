import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Extract_temp_and_humi_reducer extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();
    double min_hum = 0, min_tem = 0; // 최저 습도는 20.28, 최저 온도는 20.12
    int sum = 0;

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        if(sum == 1)
            return;
        sum++;

        String[] columns;
        double temp_hum, temp_tem;

        for(Text value : values) {
            columns = value.toString().split(",");

            try {
                temp_hum = Double.parseDouble(columns[0].substring(0, 5));
                temp_tem = Double.parseDouble(columns[1].substring(0, 5));
            }
            catch (StringIndexOutOfBoundsException e) {
                temp_hum = Double.parseDouble(columns[0]);
                temp_tem = Double.parseDouble(columns[1]);
            }

            if(min_hum == 0 && min_tem == 0) {
                min_hum = temp_hum;
                min_tem = temp_tem;
            }

            if(min_hum > temp_hum) min_hum = temp_hum;
            if(min_tem > temp_tem) min_tem = temp_tem;
        }

        result.set("\nmin_Humidity : " + min_hum + "\nmin_Temperature : " + min_tem);
        context.write(key, result);
    }
}
