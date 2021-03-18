
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WorkerRepository;
import domain.Worker;

@Component
@Transactional
public class StringToWorkerConverter implements Converter<String, Worker> {

	@Autowired
	WorkerRepository	workerRepository;


	@Override
	public Worker convert(final String text) {
		Worker result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.workerRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
