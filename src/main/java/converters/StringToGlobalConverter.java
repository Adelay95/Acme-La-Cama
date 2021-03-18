
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GlobalRepository;
import domain.Global;

@Component
@Transactional
public class StringToGlobalConverter implements Converter<String, Global> {

	@Autowired
	GlobalRepository	globalRepository;


	@Override
	public Global convert(final String text) {
		Global result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.globalRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
