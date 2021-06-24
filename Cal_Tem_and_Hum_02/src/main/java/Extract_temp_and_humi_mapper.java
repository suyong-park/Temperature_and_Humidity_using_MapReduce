import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Extract_temp_and_humi_mapper extends Mapper<LongWritable, Text, Text, Text> {

    private final Text outputKey = new Text(); // 출력 키
    private final Text outputValue = new Text(); // 출력 값

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        Extract_temp_and_humi parser = new Extract_temp_and_humi(value);

        outputKey.set(parser.getCalendar()); // key 값 : 날짜와시간
        outputValue.set(parser.getTimestamp() + "," + parser.getTemperature() + "," + parser.getHumidity()); // value 값 : 온도,습도,n/a값

        context.write(outputKey, outputValue);
    }
}
