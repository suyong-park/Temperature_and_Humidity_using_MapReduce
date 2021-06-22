import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Extract_temp_and_humi_reducer extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();
    double max_hum = 0, max_tem = 0; // 최대 습도는 39.99 최대 온도는 40
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

            if(max_hum < temp_hum) max_hum = temp_hum;
            if(max_tem < temp_tem) max_tem = temp_tem;
        }

        result.set("\nmax_Humidity : " + max_hum + "\nmax_Temperature : " + max_tem);
        context.write(key, result);
    }
}
