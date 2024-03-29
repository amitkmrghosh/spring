package com.amit.test.file.xml;

import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.amit.file.ProductFooterStaxCallback;
import com.amit.file.ProductHeaderStaxCallback;
import com.amit.model.Product;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.FlatFileReaderConfiguration;


@Configuration
@EnableBatchProcessing
@Import({ FlatFileReaderConfiguration.class })
public class JobXmlHeaderFooterFileConfiguration extends AbstractBatchConfiguration {

	public static final String OUTPUT_FILE = "target/outputs/products-headerfooter.xml";

	@Autowired
	FlatFileItemReader<Product> productItemReader;

	@Bean
	public Job writeProductJob() {
		Step step = stepBuilders.get("readWrite")
		                        .<Product, Product>chunk(10)
		                        .reader(productItemReader)
		                        .writer(productItemWriter())
		                        .listener(footerCallback())
		                        .build();

		return jobBuilders.get("writeProductJob")
		                  .start(step)
		                  .build();
	}

	@Bean
	public StaxEventItemWriter<Product> productItemWriter() {
		StaxEventItemWriter<Product> writer = new StaxEventItemWriter<Product>();
		writer.setResource(new FileSystemResource(OUTPUT_FILE));
		writer.setMarshaller(productMarshaller());
		writer.setRootTagName("products");
		writer.setOverwriteOutput(true);

		writer.setHeaderCallback(headerCallback());
		writer.setFooterCallback(footerCallback());

		return writer;
	}

	@Bean
	public XStreamMarshaller productMarshaller() {

		HashMap<String, Class> aliases = new HashMap<String, Class>();
		aliases.put("product", Product.class);

		XStreamMarshaller marshaller = new XStreamMarshaller();
		try {
			marshaller.setAliases(aliases);
		} catch (Exception ignored) {}

		return marshaller;
	}

	@Bean
	public ProductHeaderStaxCallback headerCallback() {
		return new ProductHeaderStaxCallback();
	}

	@Bean
	public ProductFooterStaxCallback footerCallback() {
		return new ProductFooterStaxCallback();
	}
}
