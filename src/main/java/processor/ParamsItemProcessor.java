package processor;

import org.springframework.batch.item.ItemProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.Params;

public class ParamsItemProcessor implements ItemProcessor<Params, Params> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParamsItemProcessor.class);
	
	@Override
	public Params process(Params params) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(params);
		LOGGER.info("processing='{}'", json);
		return params;
	}

}
