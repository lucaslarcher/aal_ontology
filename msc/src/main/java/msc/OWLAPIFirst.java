package msc;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLAPIFirst {

	public static void main(String[] args) throws OWLOntologyCreationException {
		// TODO Auto-generated method stub
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		int count = args.length;
		File file;
		if(count>0) {
			System.out.println("file:"+args[0]);
		 	file = new File(args[0]);
		}
		else {
			file = new File("aal.owl");
		}
		OWLOntology o = man.loadOntologyFromOntologyDocument(file);
		System.out.println("CLASS:");
		Set<OWLClass> classes = o.getClassesInSignature();
		Iterator<OWLClass> classesAsIterator = classes.iterator();
        while (classesAsIterator.hasNext()){
        	OWLClass it = classesAsIterator.next();
        	System.out.println(it);
        }
	}

}
