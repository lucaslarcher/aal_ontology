package owlapi;
 
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.google.common.collect.Multimap;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.search.EntitySearcher;
import uk.ac.manchester.cs.owl.explanation.ordering.Tree;

import java.io.File;
import java.util.*;

/**
 * Example how to use an OWL ontology with a reasoner.
 * <p>
 * Run in Maven with <code>mvn exec:java -Dexec.mainClass=cz.makub.Tutorial</code>
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class getOwlInfo {

    //private static final String BASE_URL = "http://acrab.ics.muni.cz/ontologies/tutorial.owl";
    private static String BASE_URL = "http://www.semanticweb.org/lucas/ontologies/2019/7/AAL";

    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();


    public static void main(String[] args) throws OWLOntologyCreationException {
       
    	
    	//prepare ontology and reasoner
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file;
        int count = args.length;
        if(count>0) {
            System.out.println("file:"+args[0]);
        	file = new File(args[0]);
        }
        else {
        	file = new File("aal.owl");
        }
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
        OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
        OWLDataFactory factory = manager.getOWLDataFactory();
        PrefixDocumentFormat pm = manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat();
        pm.setDefaultPrefix(BASE_URL + "#");

        //ajust BASE_URL
        String iri = ontology.toString();
        iri = iri.split(">")[0];
        iri = iri.split("<")[1];
        BASE_URL = iri;
        //return all individual of this type
        
        if(count>1) {
        	 //System.out.println("Individuals of:"+args[1]);
             //getTypeIndividuals(args[1],reasoner,factory,pm);
        	 System.out.println("Individual:"+args[1]);
             getInitialInfoIndividual(args[1], factory, pm, reasoner, ontology);
        }
        else {
        	//System.out.println("Individuals of:SmartFitnessBracelet");
            //getTypeIndividuals("SmartFitnessBracelet",reasoner,factory,pm);
        	System.out.println("Individual:SmartFitnessBracelet");
            getInitialInfoIndividual("SFB1", factory, pm, reasoner, ontology);
        }
        
        //get all information from individual(assertations and inferences)
    	
        
    }
    public static void getinfo(String args[]) throws OWLOntologyCreationException {
    	//prepare ontology and reasoner
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file;
        int count = args.length;
        if(count>0) {
            System.out.println("file:"+args[0]);
        	file = new File(args[0]);
        }
        else {
        	file = new File("aal.owl");
        }
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
        OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
        OWLDataFactory factory = manager.getOWLDataFactory();
        PrefixDocumentFormat pm = manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat();
        pm.setDefaultPrefix(BASE_URL + "#");

        //return all individual of this type
        
        if(count>1) {
        	 System.out.println("Individuals of:"+args[1]);
             getTypeIndividuals(args[1],reasoner,factory,pm);
        }
        else {
        	System.out.println("Individuals of:SmartFitnessBracelet");
            getTypeIndividuals("SmartFitnessBracelet",reasoner,factory,pm);
        }
        
        //get all information from individual(assertations and inferences)
    	//System.out.println("Individual:"+args[1]);
        //getInitialInfoIndividual(args[1], factory, pm, reasoner, ontology);
    }

    public static void getTypeIndividuals(String type, OWLReasoner reasoner, OWLDataFactory factory, PrefixDocumentFormat pm) {
	    //get class and its individuals
	    OWLClass individualClass = factory.getOWLClass(":"+type, pm);
	    for (OWLNamedIndividual namedIndividual : reasoner.getInstances(individualClass, false).getFlattened()) {
	        System.out.println(type+" : " + renderer.render(namedIndividual));
	    }
    }
    
    public static OWLNamedIndividual getIndividual(String named,OWLDataFactory factory, PrefixDocumentFormat pm) {
    	OWLNamedIndividual individual = factory.getOWLNamedIndividual(":"+named, pm);
    	return individual;
    }
    
    public static void getDataProperty(String nameDataProperty, String nameIndividual, OWLDataFactory factory, PrefixDocumentFormat pm,OWLReasoner reasoner) {
    	OWLNamedIndividual individual = getIndividual(nameIndividual, factory, pm);
    	OWLDataProperty dataProperty = factory.getOWLDataProperty(":"+nameDataProperty, pm);
        for (OWLLiteral literal : reasoner.getDataPropertyValues(individual, dataProperty)) {
            System.out.println(nameIndividual+" has "+nameDataProperty+": " + literal.getLiteral());
        }
    }

    public static void getAllDataProperty(String nameIndividual, OWLDataFactory factory, PrefixDocumentFormat pm,OWLReasoner reasoner) {
    	OWLNamedIndividual individual = getIndividual(nameIndividual, factory, pm);
    	OWLDataProperty dataProperty = factory.getOWLDataProperty(":*", pm);
        for (OWLLiteral literal : reasoner.getDataPropertyValues(individual, dataProperty)) {
            System.out.println(nameIndividual+" has : " + literal.getLiteral());
        }
    }
    
    public static void getObjectProperty(String property, String namedindividual, OWLDataFactory factory, PrefixDocumentFormat pm,OWLReasoner reasoner) {
		OWLNamedIndividual individual = getIndividual(namedindividual, factory, pm);
    	OWLObjectProperty objectProperty = factory.getOWLObjectProperty(":"+property, pm);
        for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(individual,objectProperty ).getFlattened()) {
	        System.out.println(namedindividual+" "+property+" at: " + renderer.render(ind));
	    }
	}
    
    public static void getInitialInfoIndividual(String nameIndividual, OWLDataFactory factory, PrefixDocumentFormat pm,OWLReasoner reasoner,OWLOntology ontology) {
    	OWLNamedIndividual individual = getIndividual(nameIndividual, factory, pm);

	    //find to which classes the individual belongs
	    Collection<OWLClassExpression> assertedClasses = EntitySearcher.getTypes(individual, ontology);
	    for (OWLClass c : reasoner.getTypes(individual, false).getFlattened()) {
	        boolean asserted = assertedClasses.contains(c);
	        System.out.println((asserted ? "asserted" : "inferred") + " class for "+nameIndividual+": " + renderer.render(c));
	    }
	    
	    Multimap<OWLObjectPropertyExpression, OWLIndividual> assertedValues = EntitySearcher.getObjectPropertyValues(individual, ontology);
	    for (OWLObjectProperty objProp : ontology.getObjectPropertiesInSignature(Imports.INCLUDED)) {
	        for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(individual, objProp).getFlattened()) {
	            boolean asserted = assertedValues.get(objProp).contains(ind);
	            System.out.println((asserted ? "asserted" : "inferred") + " object property for "+nameIndividual+": "
	                    + renderer.render(objProp) + " -> " + renderer.render(ind));
	        }
	    }
	    listAllDataPropertyValues(individual,ontology,reasoner);
	    
	}
    
    
    static void listAllDataPropertyValues(OWLNamedIndividual individual, OWLOntology ontology, OWLReasoner reasoner) {
        OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
        Multimap<OWLDataPropertyExpression, OWLLiteral> assertedValues = EntitySearcher.getDataPropertyValues(individual, ontology);
        for (OWLDataProperty dataProp : ontology.getDataPropertiesInSignature(Imports.INCLUDED)) {
            for (OWLLiteral literal : reasoner.getDataPropertyValues(individual, dataProp)) {
                Collection<OWLLiteral> literalSet = assertedValues.get(dataProp);
                boolean asserted = (literalSet != null && literalSet.contains(literal));
                System.out.println((asserted ? "asserted" : "inferred") + " data property for " + renderer.render(individual) + " : "
                        + renderer.render(dataProp) + " -> " + renderer.render(literal));
            }
        }
    }

    
    private static void printIndented(Tree<OWLAxiom> node, String indent) {
        OWLAxiom axiom = node.getUserObject();
        System.out.println(indent + renderer.render(axiom));
        if (!node.isLeaf()) {
            for (Tree<OWLAxiom> child : node.getChildren()) {
                printIndented(child, indent + "    ");
            }
        }
    }
    
    

    /**
     * Helper class for extracting labels, comments and other anotations in preffered languages.
     * Selects the first literal annotation matching the given languages in the given order.
     */
    @SuppressWarnings("WeakerAccess")
    public static class LocalizedAnnotationSelector {
        private final List<String> langs;
        private final OWLOntology ontology;
        private final OWLDataFactory factory;

        /**
         * Constructor.
         *
         * @param ontology ontology
         * @param factory  data factory
         * @param langs    list of prefered languages; if none is provided the Locale.getDefault() is used
         */
        public LocalizedAnnotationSelector(OWLOntology ontology, OWLDataFactory factory, String... langs) {
            this.langs = (langs == null) ? Collections.singletonList(Locale.getDefault().toString()) : Arrays.asList(langs);
            this.ontology = ontology;
            this.factory = factory;
        }


    }
}