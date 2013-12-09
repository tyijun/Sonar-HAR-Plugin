package blackboard.sonar.plugins.har.batch;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import blackboard.sonar.plugins.definition.Har;
import blackboard.sonar.plugins.har.resources.HarResource;

public class HarDecorator implements Decorator {

	public boolean shouldExecuteOnProject(Project project) {
		// Execute only on HAR projects
		return StringUtils.equals(project.getLanguageKey(), Har.KEY);
	}

	@SuppressWarnings("rawtypes")
	public void decorate(Resource resource, DecoratorContext context) {
		// This method is executed on the whole tree of resources.
		// Bottom-up navigation : Java methods -> Java classes -> files ->
		// packages -> modules -> project
		
		// Add execution logic here ...
		if(resource instanceof HarResource){
			HarResource har = (HarResource) resource;
			System.out.println("m size:"+har.getMeasures().size());
			if(har.getMeasures() !=null && har.getMeasures().size()>0){
				for(Measure measure: har.getMeasures()){
					context.saveMeasure(measure);
					System.out.println("measure:"+measure.getMetricKey());
				}
			}
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
