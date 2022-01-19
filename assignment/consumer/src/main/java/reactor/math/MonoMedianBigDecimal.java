package reactor.math;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.reactivestreams.Publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Computes the median of source numbers and returns the result as a {@link BigDecimal}.
 *
 * @param <T> the input value type
 */
public class MonoMedianBigDecimal<T> extends Mono<BigDecimal>  
		implements Fuseable,Scannable {

	private final Function<? super T, ? extends Number> mapping;
	
	protected final Flux<? extends T> source;

	public MonoMedianBigDecimal(Publisher<T> source,
			Function<? super T, ? extends Number> mapping) {
		this.source = Flux.from(Objects.requireNonNull(source));
		this.mapping = mapping;
	}

	@Override
	public void subscribe(CoreSubscriber<? super BigDecimal> actual) {
		source.subscribe(new MedianBigDecimalSubscriber<T>(actual, mapping));
	}

	private static final class MedianBigDecimalSubscriber<T>
			extends MathSubscriber<T, BigDecimal> {

		private final Function<? super T, ? extends Number> mapping;

		private int count;

		private List<BigDecimal> nums = new ArrayList<>();

		MedianBigDecimalSubscriber(CoreSubscriber<? super BigDecimal> actual,
				Function<? super T, ? extends Number> mapping) {
			super(actual);
			this.mapping = mapping;
		}

		@Override
		protected void reset() {
			count = 0;
			nums = new ArrayList<>();
		}

		@Override
		protected BigDecimal result() {
			if(count == 0)
				return null;
			Collections.sort(nums);
			if(nums.size() % 2 == 0)
			{
				return nums.get(nums.size()/2).add(nums.get(nums.size()/2 -1).divide(new BigDecimal(2)));
			}
			return nums.get(nums.size()/2);
		}

		@Override
		protected void updateResult(T newValue) {
			Number number = mapping.apply(newValue);
			BigDecimal bigDecimalValue = (number instanceof BigDecimal) ?
				(BigDecimal) number : new BigDecimal(number.toString());
			nums.add(bigDecimalValue);
			count++;
		}
	}

	@Override
	public Object scanUnsafe(Attr key) {
		if (key == Attr.PREFETCH) return Integer.MAX_VALUE;
		if (key == Attr.PARENT) return source;
		return null;
	}

	
}