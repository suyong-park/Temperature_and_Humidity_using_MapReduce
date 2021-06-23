import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Extract_temp_and_humi_reducer extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();
    double sum_hum = 0, sum_tem = 0;// 습도 평균 30.6120 온도 평균 29.5448
    int count = 0;
    int is_end = 0;

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        if(is_end == 1)
            return;
        is_end++;

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

            sum_hum += temp_hum;
            sum_tem += temp_tem;

            count++;
        }

        result.set("\navg_Humidity : " + sum_hum/count + "\navg_Temperature : " + sum_tem/count);
        context.write(key, result);
    }
}
