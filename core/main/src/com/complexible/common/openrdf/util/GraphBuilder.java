/*
 * Copyright (c) 2009-2013 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.complexible.common.openrdf.util;

import com.complexible.common.openrdf.model.Graphs;

import org.openrdf.model.Graph;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;

/**
 * <p>Utility class for creating a set of statements using {@link ResourceBuilder ResourceBuilders}.</p>
 *
 * @author	Michael Grove
 * @since	0.1
 * @version 3.0
 */
public class GraphBuilder {
    private final Graph mGraph;
    private final ValueFactory mValueFactory;

    public GraphBuilder() {
        mGraph = Graphs.newGraph();
        mValueFactory = ValueFactoryImpl.getInstance();
    }

    public GraphBuilder(final ValueFactory theValueFactory) {
        mGraph = Graphs.newGraph();
        mValueFactory = theValueFactory;
    }

	/**
	 * Return the Graph created via this builder
	 *
	 * @return  the graph
	 */
    public Graph graph() {
        return Graphs.newGraph(mGraph);
    }

	/**
	 * Clear the contents of the builder
	 */
    public void reset() {
        mGraph.clear();
    }

	/**
	 * Create a {@link ResourceBuilder builder} for the individual
	 * @param theURI    the individual
	 * @return          the {@link ResourceBuilder builder}
	 */
    public ResourceBuilder uri(URI theURI) {
        return new ResourceBuilder(mGraph, getValueFactory(), getValueFactory().createURI(theURI.toString()));
    }

	/**
	 * Create a {@link ResourceBuilder builder} for the individual
	 * @param theURI    the individual
	 * @return          the {@link ResourceBuilder builder}
	 */
    public ResourceBuilder uri(String theURI) {
        return instance(null, theURI);
    }

	/**
	 * Create a new anonymous instance of the given type
	 *
	 * @param theType   the type
	 *
	 * @return          a {@link ResourceBuilder builder} for the new individual
	 */
    public ResourceBuilder instance(URI theType) {
        return instance(theType, (String) null);
    }

	/**
	 * Create an un-typed anonymous individual in the graph
	 *
	 * @return a ResourceBuilder wrapping the bnode
	 */
	public ResourceBuilder instance() {
		return instance(null, (String) null);
	}


	/**
	 * Create a {@link ResourceBuilder builder} for the given individual and add the type
	 *
	 * @param theType   the type
	 * @param theURI    the individual
	 *
	 * @return          a {@link ResourceBuilder builder} for the new individual
	 */
	public ResourceBuilder instance(URI theType, java.net.URI theURI) {
		return instance(theType, theURI.toString());
	}

	/**
	 * Create a {@link ResourceBuilder builder} for the given individual and add the type
	 *
	 * @param theType   the type
	 * @param theRes    the individual
	 *
	 * @return          a {@link ResourceBuilder builder} for the new individual
	 */
	public ResourceBuilder instance(URI theType, Resource theRes) {
		if (theType != null) {
			mGraph.add(theRes, RDF.TYPE, theType);
		}

		return new ResourceBuilder(mGraph, getValueFactory(), theRes);
	}
	/**
	 * Create a {@link ResourceBuilder builder} for the given individual and add the type
	 *
	 * @param theType   the type
	 * @param theURI    the individual
	 *
	 * @return          a {@link ResourceBuilder builder} for the new individual
	 */
    public ResourceBuilder instance(URI theType, String theURI) {
        Resource aRes = theURI == null
                        ? getValueFactory().createBNode()
                        : getValueFactory().createURI(theURI);

        if (theType != null) {
            mGraph.add(aRes, RDF.TYPE, theType);
        }

        return new ResourceBuilder(mGraph, getValueFactory(), aRes);
    }

	public ValueFactory getValueFactory() {
		return mValueFactory;
	}
}
