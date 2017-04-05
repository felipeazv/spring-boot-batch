package configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import listener.JobCompletionNotificationListener;
import pojo.Params;
import processor.ParamsItemProcessor;
import writer.ParamsItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job processParamsJob() {
		return jobBuilderFactory.get("processParamsJob").incrementer(new RunIdIncrementer()).listener(listener())
				.flow(orderStep()).end().build();
	}

	@Bean
	public Step orderStep() {
		return stepBuilderFactory.get("orderStep").<Params, Params>chunk(3).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	@Bean
	public FlatFileItemReader<Params> reader() {
		FlatFileItemReader<Params> reader = new FlatFileItemReader<Params>();
		reader.setResource(new ClassPathResource("DATA.csv"));
		reader.setLineMapper(new DefaultLineMapper<Params>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "param1", "param2", "param3", "param4", "param5", "param6", "param7",
								"param8", "param9", "param10", "param11", "param12", "param13", "param14", "param15",
								"param16", "param17", "param18", "param19", "param20", "param21", "param22", "param23",
								"param24", "param25", "param26", "param27", "param28", "param29", "param30" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Params>() {
					{
						setTargetType(Params.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public ParamsItemProcessor processor() {
		return new ParamsItemProcessor();
	}

	@Bean
	public ItemWriter<Params> writer() {
		return new ParamsItemWriter();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}
}
