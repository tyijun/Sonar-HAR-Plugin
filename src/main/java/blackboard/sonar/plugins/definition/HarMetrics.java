package blackboard.sonar.plugins.definition;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public final class HarMetrics implements Metrics {

	public static final String HAR_DOMAIN = "HAR Analysis";

	public static final Metric BUILD = new Metric.Builder("learn.har.build",
			"Learn Build", Metric.ValueType.STRING)
			.setDescription(
					"This is a metric to store the build of learn that the HAR running on.")
			.setDirection(Metric.DIRECTION_NONE).setQualitative(false)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric IMPORT_TIME = new Metric.Builder(
			"learn.har.importTime", "Import at", Metric.ValueType.STRING)
			.setDescription("This is a metric to store the import time")
			.setDirection(Metric.DIRECTION_NONE).setQualitative(false)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric RESP_TIME_MIN = new Metric.Builder(
			"learn.har.respTimeMin", "Min Response Time",
			Metric.ValueType.FLOAT)
			.setDescription("This is a metric to store the min response time")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric RESP_TIME_MAX = new Metric.Builder(
			"learn.har.respTimeMax", "Max Response Time",
			Metric.ValueType.FLOAT)
			.setDescription("This is a metric to store the max response time")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric RESP_TIME_AVG = new Metric.Builder(
			"learn.har.respTimeAvg", "AVG Response Time",
			Metric.ValueType.FLOAT)
			.setDescription("This is a metric to store the avg response time")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric RESP_TIME_STDEV = new Metric.Builder(
			"learn.har.respTimeStdev", "Stdev Response Time",
			Metric.ValueType.FLOAT)
			.setDescription(
					"This is a metric to store the response time standard deviation")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric TOTAL_HTTP400 = new Metric.Builder(
			"learn.har.http400Ct", "Total Http400", Metric.ValueType.INT)
			.setDescription("This is a metric to store the Total Http400")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric AVG_HTTP400 = new Metric.Builder(
			"learn.har.http400AvgCt", "Avg Http400", Metric.ValueType.INT)
			.setDescription("This is a metric to store the min response time")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric TOTAL_HTTP500 = new Metric.Builder(
			"learn.har.http500Ct", "Total Http500", Metric.ValueType.INT)
			.setDescription("This is a metric to store the Total Http500")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();
	public static final Metric AVG_HTTP500 = new Metric.Builder(
			"learn.har.http500AvgCt", "Avg Http500", Metric.ValueType.INT)
			.setDescription("This is a metric to store the Avg Http500")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(true)
			.setDomain(HAR_DOMAIN).create();

	// getMetrics() method is defined in the Metrics interface and is used by
	// Sonar to retrieve the list of new metrics
	public List<Metric> getMetrics() {
		return Arrays.asList(BUILD, IMPORT_TIME, RESP_TIME_MIN, RESP_TIME_MAX,
				RESP_TIME_AVG, RESP_TIME_STDEV, TOTAL_HTTP400, AVG_HTTP400,
				TOTAL_HTTP500, AVG_HTTP500);
	}
}
