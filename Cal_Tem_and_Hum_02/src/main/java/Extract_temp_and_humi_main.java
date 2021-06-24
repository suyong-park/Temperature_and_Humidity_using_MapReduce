import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Extract_temp_and_humi_main  {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        if(args.length != 2) {
            System.err.println("INPUT ERROR!!! Usage : extract_temp_and_humi_main <input> <output>");
            System.exit(2);
        }

        Job job = new Job(conf, "extract_temp_and_humi");

        job.setJarByClass(Extract_temp_and_humi_main.class);
        job.setMapperClass(Extract_temp_and_humi_mapper.class);
        job.setReducerClass(Extract_temp_and_humi_reducer.class);
        // 사용할 클래스 결정

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        // Input, Output Format 결정

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        System.out.println(success);
    }
}