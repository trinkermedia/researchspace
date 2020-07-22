/**
 * ResearchSpace
 * Copyright (C) 2020, © Trustees of the British Museum
 * Copyright (C) 2015-2019, metaphacts GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.researchspace.data.rdf.container;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.researchspace.data.rdf.PointedGraph;
import org.researchspace.repository.MpRepositoryProvider;
import org.researchspace.rest.endpoint.FormPersistenceLdpEndpoint;
import org.researchspace.vocabulary.LDP;

import com.google.common.base.Throwables;

/**
 * Parent container to store and manage containers generated by client-side
 * workflows, which operate in a container mode. Each child container contains a
 * set of statements generated by any client-side workflow. With every update of
 * a particular workflow instance, the entire child container (for that
 * particular workflow) will be replaced by a new set of statements. Used by
 * {@link FormPersistenceLdpEndpoint}.
 * 
 * @author Petr Nikolaev <pn@metaphacts.com>
 */
@LDPR(iri = WorkflowContainer.IRI_STRING)
public class WorkflowContainer extends DefaultLDPContainer {

    private static final Logger logger = LogManager.getLogger(WorkflowContainer.class);

    public static final String IRI_STRING = "http://www.researchspace.org/resource/system/workflowContainer";
    public static final IRI IRI = vf.createIRI(IRI_STRING);

    public WorkflowContainer(IRI iri, MpRepositoryProvider repositoryProvider) {
        super(iri, repositoryProvider);
    }

    @Override
    public void initialize() {
        logger.debug("Initalizing workflow root container.");
        if (!getReadConnection().hasOutgoingStatements(this.getResourceIRI())) {
            LinkedHashModel m = new LinkedHashModel();
            m.add(vf.createStatement(IRI, RDF.TYPE, LDP.Container));
            m.add(vf.createStatement(IRI, RDF.TYPE, LDP.Resource));
            m.add(vf.createStatement(IRI, RDFS.LABEL, vf.createLiteral("Workflow Container")));
            m.add(vf.createStatement(IRI, RDFS.COMMENT,
                    vf.createLiteral("Container to store Workflow Instantiations.")));
            try {
                getRootContainer().add(new PointedGraph(IRI, m));
            } catch (RepositoryException e) {
                throw Throwables.propagate(e);
            }
        }
    }

    @Override
    public IRI add(PointedGraph pointedGraph) throws RepositoryException {
        return super.add(pointedGraph);
    }

    @Override
    public void update(PointedGraph pointedGraph) throws RepositoryException {
        super.update(pointedGraph);
    }

}