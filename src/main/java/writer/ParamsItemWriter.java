package writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import pojo.Params;

public class ParamsItemWriter implements ItemWriter<Params> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParamsItemWriter.class);
	
	@Override
	public void write(List<? extends Params> items) throws Exception {
		LOGGER.info("send to message broker");
	}

}
