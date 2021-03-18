
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Worker;

@Component
@Transactional
public class WorkerToStringConverter implements Converter<Worker, String> {

	@Override
	public String convert(final Worker worker) {
		String result;

		if (worker == null)
			result = null;
		else
			result = String.valueOf(worker.getId());

		return result;
	}

}
