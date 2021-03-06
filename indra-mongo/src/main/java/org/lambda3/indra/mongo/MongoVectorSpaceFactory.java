package org.lambda3.indra.mongo;

/*-
 * ==========================License-Start=============================
 * Indra Mongo Module
 * --------------------------------------------------------------------
 * Copyright (C) 2016 - 2017 Lambda^3
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ==========================License-End===============================
 */

import com.mongodb.MongoClient;
import org.lambda3.indra.core.Params;
import org.lambda3.indra.core.VectorSpaceFactory;
import org.lambda3.indra.core.composition.VectorComposer;
import org.lambda3.indra.core.exception.ModelNoFound;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class MongoVectorSpaceFactory extends VectorSpaceFactory {

    private MongoClient mongoClient;

    public MongoVectorSpaceFactory(String mongoURI) {
        this(new MongoClient(mongoURI));
    }

    public MongoVectorSpaceFactory(MongoClient mongoClient) {
        this.mongoClient = Objects.requireNonNull(mongoClient);
    }

    @Override
    public MongoVectorSpace doCreate(Params params) throws ModelNoFound {
        if (getAvailableModels().contains(getDBName(params))) {
            VectorComposer termComposer = this.vectorComposerFactory.getComposer(params.termComposition);
            VectorComposer translationComposer = this.vectorComposerFactory.getComposer(params.translationComposition);
            return new MongoVectorSpace(mongoClient, getDBName(params), termComposer, translationComposer);
        }

        throw new ModelNoFound(getDBName(params));
    }

    @Override
    public Params createKey(Params params) {
        return params;
    }

    private String getDBName(Params params) {
        return String.format("%s-%s-%s",
                params.model.toLowerCase(),
                params.translate ? params.translateTargetLanguage.toLowerCase() : params.language.toLowerCase(),
                params.corpusName.toLowerCase());
    }

    @Override
    public Collection<String> getAvailableModels() {
        Set<String> availableModels = new HashSet<>();
        for (String s : mongoClient.listDatabaseNames()) {
            if (!s.equalsIgnoreCase("admin") || s.equalsIgnoreCase("local")) {
                availableModels.add(s);
            }
        }
        return availableModels;
    }
}
