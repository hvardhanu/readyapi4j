package com.smartbear.readyapi4j.assertions;

import com.smartbear.readyapi.client.model.XPathContainsAssertion;

public interface XPathAssertionBuilder extends AssertionBuilder<XPathContainsAssertion> {
    XPathAssertionBuilder allowWildCards();

    XPathAssertionBuilder ignoreComments();

    XPathContainsAssertionBuilder ignoreNamespaces();

    XPathAssertionBuilder named(String assertionName);
}
