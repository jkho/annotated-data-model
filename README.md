services-data-model
===================

In spite of the content of [my google document](https://docs.google.com/a/basistech.com/document/d/1a3SiHdRjjB1jUWW_orpcnmEQD3KvCqUa9oOQT0u18uA/edit),
the comments of James and others have convinced me that an annotation model is the wrong place to start. Coding to a pure annotation model is pure
hell. If we need to layer an abstract data model over the annotation model to get work done, we might as well start with the abstract model. It
would enable implementation of the web service. Thus, this repo contains a sketch in actual code.

# Data Model Overview #

The top-level item in the model is a `Text`. A Text contains a blob, not surprisingly, of text. A Text has a collection
of attributes. An attribute has a name and a value; the general restriction is that the value must be serializable
with Jackson. Attributes can, as a result have attibutes.

## Top-level Attributes ##

* Sentence Boundaries: just as in RLP, a sentence boundary is just a marker in the text blob. There can be other boundaries.
* RLI attributes: such as overall languages detected and language region boundaries.
* Tokens: Much of the complexity of all this adheres to the tokens.
* Named Entities: and other spans.

## Mutability ##

Currently, all the attribute classes are immutable. The idea is that mutation requires making a new one.
If we decide that this bit of functional programming thinking is in fact evidence of confused thinking, we can add
setters.

## Token Data Model ##

We need to be able to represent the current output of RLP. This is a bit messy. In RLP, a token can have a set of 'alternative lemmas',
and a set of 'alternative POS tags', and the two _are not connected_. This is more complex than the model of RBL-JE, which
is that a token has one or more analyses, each consisting of a collection of attributes such as lemma and POS.

An obvious solution here is to give a token a set of attributes, each of which can be multi-valued: a list of POS, a list of lemmas,
etc. Then we need to be able to indicate the interconnections when they are present. A solution here would be a `Text`-level
boolean attribute that indicates that the token attributes correlate into 'analyses' (or not).

A related issue is the matter of disambiguated output. In RLP, we have distinct attributes for the 'right answer': Part Of Speech
is a different attribute from 'alternative parts of speech'. In RBL-JE, we have an attribute that identifies the 'selected analysis'.

David Murgatroyd has pointed out that the concept of 'correct' or 'selected' is a stand-in for an ordering. So, one path to take is that
disambiguation reorders the multi-valued attributes so that the right answer is in the \[0] slot. If we ever do a full
ranking component, it could deliver a full order. I think that this is preferable to the RLP-like solution of a additional attributes
to contain the selected solution from amongst the multiple alternatives.

## Entity Data Model ##

Entities have offsets, type, confidence, and chainId. Keeping an integer chain ID is a compromise; we could add a new top-level
attribute called 'CoreferenceChain' that stored a list of EntityMentions. That would require care in serialization and deserialization,
but I skipped that work for the moment.


