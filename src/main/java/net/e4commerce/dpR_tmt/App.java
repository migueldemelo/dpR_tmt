package net.e4commerce.dpR_tmt;

import java.util.Optional;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Hello world!
 *
 */
public class App extends ResourceConfig{
	
	
	
	
    public static void main( String[] args )
    {
        Repository rep = 
//    	new SailRepository(new MemoryStore());
    	new SailRepository(
    			new MemoryStore()
//  			  new ForwardChainingRDFSInferencer(new MemoryStore())
  			  );
        rep.initialize();
        
        String namespace = "http://example.org/";
        ValueFactory f = rep.getValueFactory();
        IRI john = f.createIRI(namespace, "john");
        
        try (RepositoryConnection conn = rep.getConnection()) {
        	conn.add(john, RDF.TYPE, FOAF.PERSON);
        	conn.add(john, RDFS.LABEL, f.createLiteral("John"));
        	conn.add(john, RDFS.MEMBER, f.createIRI(namespace, "#john"));
        	
//        	RepositoryResult<Statement> statements = conn.getStatements(null, null, null);
//        	Model model = QueryResults.asModel(statements);
//        	model.setNamespace(RDF.NS);
//        	model.setNamespace(RDFS.NS);
//        	model.setNamespace(FOAF.NS);
//        	model.setNamespace("ex", namespace);
        	
//        	ModelBuilder builder = new ModelBuilder();
//
//        	// set some namespaces
//        	builder.setNamespace("ex", "http://example.org/").setNamespace(FOAF.NS);
//
//        	builder.namedGraph("ex:graph1")      // add a new named graph to the model
//        	     .subject("ex:john")        // add  several statements about resource ex:john
//        		 .add(FOAF.NAME, "John")  // add the triple (ex:john, foaf:name "John") to the named graph
//        		 .add(FOAF.AGE, 42)
//        		 .add(RDF.TYPE, FOAF.PERSON)
//        		 .add(FOAF.MBOX, "john@example.org");
//        	
//        	builder.namedGraph("ex:graph1")      // add a new named graph to the model
//   			.subject("ex:john2")        // add  several statements about resource ex:john
//	 		 .add(FOAF.NAME, "John2")  // add the triple (ex:john, foaf:name "John") to the named graph
//	 		 .add(FOAF.AGE, 43)
//	 		 .add(RDF.TYPE, FOAF.PERSON)
//	 		 .add(FOAF.MBOX, "john2@example.org");
//
//        	// add a triple to the default graph
//        	builder.defaultGraph().add("ex:graph1", RDF.TYPE, "ex:Graph");
//
//        	// return the Model object
//        	Model m = builder.build();
//        	
//        	
//        	for (Resource person: m.filter(null, RDF.TYPE, FOAF.PERSON).subjects()) {
//        		  // get the name of the person (if it exists)
//        		  Optional<Literal> name = Models.objectLiteral(m.filter(person, FOAF.NAME, null));
//        		  System.out.println(name);
//        		}
//        	for (Resource person: m.filter(null, RDF.TYPE, FOAF.PERSON).subjects()) {
//        		  // get all name-values of the person
//        		  Set<Literal> names = Models.getPropertyLiterals(m, person, FOAF.NAME);
//        		  System.out.println(names);
//        		}
//        	Rio.write(m, System.out, RDFFormat.TURTLE);
//        	
        	
        	String queryString = "SELECT ?x ?p ?y WHERE { ?x ?p ?y .} ";
        	TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
        	TupleQueryResult result = tupleQuery.evaluate();
        	
        	try (TupleQueryResult result1 = tupleQuery.evaluate()) 
        	{
        		while (result1.hasNext()) 
        		{  // iterate over the result
        		BindingSet bindingSet = result1.next();
        		   Value valueOfX = bindingSet.getValue("x");
        		   Value valueOfY = bindingSet.getValue("y");
        		   Value valueOfP = bindingSet.getValue("p");
        		   // do something interesting with the values here...
        		   System.out.println(valueOfX.stringValue());
        		   System.out.println(valueOfP.stringValue());
        		   System.out.println(valueOfY.stringValue());
        		   System.out.println("=======");
        		}
		   }
        }
    }
}
