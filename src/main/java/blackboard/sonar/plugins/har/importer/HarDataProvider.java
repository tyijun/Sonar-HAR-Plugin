package blackboard.sonar.plugins.har.importer;

import java.util.List;
import java.util.Map;

import org.sonar.api.measures.Measure;

import blackboard.sonar.plugins.har.resources.HarResource;

public interface HarDataProvider {
	
	public Map<HarResource,List<Measure>> buildData();

}
