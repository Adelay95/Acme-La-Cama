
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ServicesRepository;
import domain.Services;

@Component
@Transactional
public class StringToServiceConverter implements Converter<String, Services> {

	@Autowired
	ServicesRepository	serviceRepository;


	@Override
	public Services convert(final String text) {
		Services result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.serviceRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
