import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Extract_temp_and_humi_reducer extends Reducer<Text, Text, Text, Text> {

    private final Text value_result = new Text();
    double min_tem = 0, min_hum = 0;
    double max_tem = 0, max_hum = 0;
    double sum_tem = 0, sum_hum = 0;
    int count = 0;

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        String[] value_columns;
        double temp_min_tem, temp_min_hum, temp_max_tem, temp_max_hum;

        for(Text value : values) {

            value_columns = value.toString().split(","); // 0번째는 타임스탬프, 1번째는 온도, 2번째는 습도

            try {
                temp_max_tem = Double.parseDouble(value_columns[1].substring(0, 5));
                temp_max_hum = Double.parseDouble(value_columns[2].substring(0, 5));
                temp_min_tem = Double.parseDouble(value_columns[1].substring(0, 5));
                temp_min_hum = Double.parseDouble(value_columns[2].substring(0, 5));
            } catch (StringIndexOutOfBoundsException e) {
                temp_max_tem = Double.parseDouble(value_columns[1]);
                temp_max_hum = Double.parseDouble(value_columns[2]);
                temp_min_tem = Double.parseDouble(value_columns[1]);
                temp_min_hum = Double.parseDouble(value_columns[2]);
            }

            sum_tem += Double.parseDouble(value_columns[1]);
            sum_hum += Double.parseDouble(value_columns[2]);

            if(max_tem < temp_max_tem) max_tem = temp_max_tem;
            if(max_hum < temp_max_hum) max_hum = temp_max_hum;

            if(min_tem == 0 && min_hum == 0) {
                min_tem = temp_min_tem;
                min_hum = temp_min_hum;
            }

            if(min_tem > temp_min_hum) min_tem = temp_min_tem;
            if(min_hum > temp_min_hum) min_hum = temp_min_hum;

            count++;
        }
        
        value_result.set("\nMax_Temperature : " + max_tem + "\nMax_Humidity : " + max_hum +
                "\nMin_Temperature : " + min_tem + "\nMin_Humidity : " + min_hum +
                "\nAvg_Temperature : " + sum_tem/count + "\nAvg_Humidity : " + sum_hum/count + "\n");
        context.write(key, value_result);

        min_hum = 0; min_tem = 0; max_hum = 0; max_tem = 0; sum_tem = 0; sum_hum = 0; count = 0;
    }
}
