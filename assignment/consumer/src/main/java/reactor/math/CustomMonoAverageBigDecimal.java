package reactor.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.function.Function;

import org.reactivestreams.Publisher;

import lombok.extern.slf4j.Slf4j;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.publisher.Flux;

/**
 * Computes the average of source numbers and returns the result as a
 * {@link BigDecimal}.
 *
 * @param <T> the input value type
 * <p>This class is to solve java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.</p>
 */
@Slf4j
public class CustomMonoAverageBigDecimal<T> extends MonoFromFluxOperator<T, BigDecimal> implements Fuseable {

	private final Function<? super T, ? extends Number> mapping;

	public CustomMonoAverageBigDecimal(Publisher<T> source, Function<? super T, ? extends Number> mapping) {
		super(Flux.from(source));
		this.mapping = mapping;
	}

	@Override
	public void subscribe(CoreSubscriber<? super BigDecimal> actual) {
		source.subscribe(new AverageBigDecimalSubscriber<T>(actual, mapping));
	}

	private static final class AverageBigDecimalSubscriber<T> extends MathSubscriber<T, BigDecimal> {

		private final Function<? super T, ? extends Number> mapping;

		private int count;

		private BigDecimal sum = BigDecimal.ZERO;

		AverageBigDecimalSubscriber(CoreSubscriber<? super BigDecimal> actual,
				Function<? super T, ? extends Number> mapping) {
			super(actual);
			this.mapping = mapping;
		}

		@Override
		protected void reset() {
			count = 0;
			sum = BigDecimal.ZERO;
		}

		@Override
		protected BigDecimal result() {
			if (count == 0)
				return null;
			try {
				return sum.divide(BigDecimal.valueOf(count));
			} catch (ArithmeticException ae) {
				log.info("ArithmeticException {}", ae.getMessage());
				return sum.divide(BigDecimal.valueOf(count), MathContext.DECIMAL32);
			}
		}

		@Override
		protected void updateResult(T newValue) {
			Number number = mapping.apply(newValue);
			BigDecimal bigDecimalValue = (number instanceof BigDecimal) ? (BigDecimal) number
					: new BigDecimal(number.toString());
			sum = sum.add(bigDecimalValue);
			count++;
		}
	}
}
