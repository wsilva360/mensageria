package br.gov.sp.iamspe.mensageria.config;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		// modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE).setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
		Converter<String, byte[]> convertStringToArrayByte = new AbstractConverter<String, byte[]>() {
			@Override
			protected byte[] convert(String source) {
				return (source == null) ? null : Base64.getMimeDecoder().decode(source.split(",")[1]);
			}
		};

		Converter<byte[], String> convertArrayByteToString = new AbstractConverter<byte[], String>() {
			@Override
			protected String convert(byte[] source) {
				return (source == null) ? null : new String(Base64.getMimeEncoder().encodeToString(source));
			}
		};

		Converter<Map<String, Object>, String> convertMapToJson = new AbstractConverter<Map<String, Object>, String>() {
			@Override
			protected String convert(Map<String, Object> source) {
				return (source == null || source.isEmpty()) ? null : new JSONObject(source).toString();
			}
		};

		Converter<List<String>, String> convertListToString = new AbstractConverter<List<String>, String>() {
			@Override
			protected String convert(List<String> source) {
				return (source == null) ? null
						: String.join(",", source.stream().filter(e -> StringUtils.isNotEmpty(e))
								.filter(e -> StringUtils.isNotBlank(e)).collect(Collectors.toList()));
			}
		};

		modelMapper.addConverter(convertStringToArrayByte);
		modelMapper.addConverter(convertArrayByteToString);
		modelMapper.addConverter(convertMapToJson);
		modelMapper.addConverter(convertListToString);

		return modelMapper;
	}
}