package com.scienceminer.nerd.kb;

import com.scienceminer.nerd.kb.db.KBUpperEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An language-independent atomic element of the (N)ERD Knowledge Base.
 * 
 */
public class Concept {
	protected static final Logger LOGGER = LoggerFactory.getLogger(Concept.class);

	private KBUpperEnvironment env = null;
	private String wikidataId = null;

	public Concept(KBUpperEnvironment env, String wikidataId) {
		this.env = env;
		this.wikidataId = wikidataId;
	}

	public String getId() {
		return wikidataId;
	}

	public Integer getPageIdByLang(String lang) {
		Map<String,Integer> localMap = env.getDbConcepts().retrieve(wikidataId);
		return localMap.get(lang); 
	}

	public String getLabelByLang(String lang) {
		// Hack : only the en label is supported for the moment
		if (lang.equals("en")) {
			Optional<Statement> enLabelSt = env.getDbStatements().retrieve(wikidataId).stream().filter(st -> st.getPropertyId().equals("P0")).findFirst();
			return enLabelSt.isPresent() ? enLabelSt.get().getValue() : null;
		}
		return null;
	}
	/**
	 * Return the list of statements associated to the concept
	 */
	public List<Statement> getStatements() {
		return env.getDbStatements().retrieve(wikidataId);
	}
}