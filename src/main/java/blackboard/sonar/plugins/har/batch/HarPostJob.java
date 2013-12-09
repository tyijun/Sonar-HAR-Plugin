package blackboard.sonar.plugins.har.batch;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.CheckProject;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;

import blackboard.sonar.plugins.definition.Har;

public class HarPostJob implements PostJob, CheckProject {

	public static final Logger LOG = LoggerFactory.getLogger(HarPostJob.class);

	public boolean shouldExecuteOnProject(Project project) {
		// Execute only on HAR projects
		return StringUtils.equals(project.getLanguageKey(), Har.KEY);
	}

	public void executeOn(Project project, SensorContext context) {
		/*
		 * PostJobs are executed when project is analyzed and executed only on
		 * root project, not on modules.
		 */
		// add execution logic here...
	}

}
