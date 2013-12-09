package blackboard.sonar.plugins.har.resources;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Directory;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;
import org.sonar.api.utils.WildcardPattern;

import blackboard.sonar.plugins.definition.Har;

public class HarResource extends Resource<Directory> {
	
	private List<Measure> measures = new ArrayList<Measure>();
	private String name;
	
	public HarResource(String key){
		this(key,key);
	}
	
	public HarResource(String key,String name){
		setKey(key);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getLongName() {
		return getName();
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public Language getLanguage() {
		return Har.INSTANCE;
	}

	@Override
	public String getScope() {
		return Scopes.DIRECTORY;
	}

	@Override
	public boolean matchFilePattern(String antPattern) {
		WildcardPattern matcher = WildcardPattern.create(antPattern, "/");
		return matcher.match(getKey());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("key", getKey())
				.append("language", Har.INSTANCE)
				.append("qualifier", getQualifier()).toString();
	}
	
	public void addMeasure(Measure measure){
		measures.add(measure);
	}
	
	public List<Measure> getMeasures(){
		return measures;
	}

	@Override
	public Directory getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQualifier() {
		// TODO Auto-generated method stub
		return Qualifiers.DIRECTORY;
	}

}
