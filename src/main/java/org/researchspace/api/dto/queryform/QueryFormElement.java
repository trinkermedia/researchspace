/**
 * ResearchSpace
 * Copyright (C) 2020, © Trustees of the British Museum
 * Copyright (C) 2015-2016, metaphacts GmbH
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
package org.researchspace.api.dto.queryform;

import org.eclipse.rdf4j.model.IRI;
import org.researchspace.api.dto.InconsistentDtoException;
import org.researchspace.api.dto.base.DTOBase;
import org.researchspace.api.dto.querytemplate.QueryArgument;
import org.researchspace.api.dto.querytemplate.QueryTemplate;
import org.researchspace.api.dto.uiform.UIFormElement;
import org.researchspace.vocabulary.QF;

/**
 * POJO representation of a {@link QF#QUERY_FORM_CLASS}. Holds all
 * meta-information to enable any client application to render an form element
 * which is to populate the {@link QueryTemplate} i.e. each
 * {@link QueryFormElement} corresponds to exactly one {@link QueryArgument}
 * 
 * @author jt
 *
 */
public class QueryFormElement extends DTOBase {

    private static final long serialVersionUID = -169848457057988770L;

    private IRI suggestionQueryId = null;

    private QueryArgument queryArgument;

    private int elementOrderNr = 0;

    private UIFormElement uiFormElement;

    public QueryFormElement(IRI id, String label, String description, QueryArgument queryArgument,
            UIFormElement uiElement) {
        super(id, label, description);
        this.setUiFormElement(uiElement);
        this.setQueryArgument(queryArgument);
    }

    /**
     * TODO: this may be replaced in future versions by a proper mechanism for
     * suggestions; also, it would make more sense to point to a query template
     * rather than a query (or a dedicated composed "QuerySuggestion) object;
     * anyway, consider this feature experimental for now.
     * 
     * @return pointer to the {@link org.researchspace.api.dto.query.Query} object
     *         that should be used to generate a list of suggestion values,
     *         <code>null</null> if not suggestion query exists
     */
    public IRI getSuggestionQueryId() {
        return suggestionQueryId;
    }

    public void setSuggestionQueryId(IRI suggestionQueryId) {
        this.suggestionQueryId = suggestionQueryId;
    }

    public QueryArgument getQueryArgument() {
        return queryArgument;
    }

    public void setQueryArgument(QueryArgument queryArgument) {
        this.queryArgument = queryArgument;
    }

    /**
     * @return default: 0
     */
    public int getElementOrderNr() {
        return elementOrderNr;
    }

    public void setElementOrderNr(int elementOrderNr) {
        this.elementOrderNr = elementOrderNr;
    }

    public UIFormElement getUiFormElement() {
        return uiFormElement;
    }

    public void setUiFormElement(UIFormElement uiFormElement) {
        this.uiFormElement = uiFormElement;
    }

    /**
     * Delegates to {@link QueryArgument}. It is up to the application how to render
     * this information i.e. with *
     * 
     * @return
     */
    public boolean isRequired() {
        return queryArgument.isRequired();
    }

    @Override
    public void assertConsistency() throws InconsistentDtoException {

        super.assertConsistency();

        // only the ID is mandatory
        if (queryArgument == null) {
            throw new InconsistentDtoException(this.getClass(), "queryArgument is null", getId());
        }
        if (uiFormElement == null) {
            throw new InconsistentDtoException(this.getClass(), "uiFormElement is null", getId());
        }
    }
}