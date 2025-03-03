# Anserini Regressions: TREC 2020 Deep Learning Track (Document)

**Model**: uniCOIL (without any expansions) on segmented documents (title/segment encoding)

This page describes regression experiments, integrated into Anserini's regression testing framework, using uniCOIL (without any expansions) on the [TREC 2020 Deep Learning Track document ranking task](https://trec.nist.gov/data/deep2020.html).
The uniCOIL model is described in the following paper:

> Jimmy Lin and Xueguang Ma. [A Few Brief Notes on DeepImpact, COIL, and a Conceptual Framework for Information Retrieval Techniques.](https://arxiv.org/abs/2106.14807) _arXiv:2106.14807_.

The experiments on this page are not actually reported in the paper.
However, the model is the same, applied to the MS MARCO _segmented_ document corpus (without any expansions).
Retrieval uses MaxP technique, where we select the score of the highest-scoring passage from a document as the score for that document to produce a document ranking.

The exact configurations for these regressions are stored in [this YAML file](../src/main/resources/regression/dl20-doc-segmented-unicoil-noexp.yaml).
Note that this page is automatically generated from [this template](../src/main/resources/docgen/templates/dl20-doc-segmented-unicoil-noexp.template) as part of Anserini's regression pipeline, so do not modify this page directly; modify the template instead and then run `bin/build.sh` to rebuild the documentation.

From one of our Waterloo servers (e.g., `orca`), the following command will perform the complete regression, end to end:

```
python src/main/python/run_regression.py --index --verify --search --regression dl20-doc-segmented-unicoil-noexp
```

## Corpus

We make available a version of the MS MARCO segmented document corpus that has already been processed with uniCOIL, i.e., gone through document expansion and term reweighting.
Thus, no neural inference is involved.
For details on how to train uniCOIL and perform inference, please see [this guide](https://github.com/luyug/COIL/tree/main/uniCOIL).

Download the corpus and unpack into `collections/`:

```
wget https://rgw.cs.uwaterloo.ca/JIMMYLIN-bucket0/data/msmarco-doc-segmented-unicoil-noexp.tar -P collections/

tar xvf collections/msmarco-doc-segmented-unicoil-noexp.tar -C collections/
```

To confirm, `msmarco-doc-segmented-unicoil-noexp.tar` is 11 GB and has MD5 checksum `11b226e1cacd9c8ae0a660fd14cdd710`.

With the corpus downloaded, the following command will perform the complete regression, end to end, on any machine:

```
python src/main/python/run_regression.py --index --verify --search --regression dl20-doc-segmented-unicoil-noexp \
  --corpus-path collections/msmarco-doc-segmented-unicoil-noexp
```

Alternatively, you can simply copy/paste from the commands below and obtain the same results.

## Indexing

Sample indexing command:

```
target/appassembler/bin/IndexCollection \
  -collection JsonVectorCollection \
  -input /path/to/msmarco-doc-segmented-unicoil-noexp \
  -index indexes/lucene-index.msmarco-doc-segmented-unicoil-noexp/ \
  -generator DefaultLuceneDocumentGenerator \
  -threads 16 -impact -pretokenized \
  >& logs/log.msmarco-doc-segmented-unicoil-noexp &
```

The directory `/path/to/msmarco-doc-segmented-unicoil-noexp/` should point to the corpus downloaded above.

The important indexing options to note here are `-impact -pretokenized`: the first tells Anserini not to encode BM25 doclengths into Lucene's norms (which is the default) and the second option says not to apply any additional tokenization on the uniCOIL tokens.
Upon completion, we should have an index with 20,545,677 documents.

For additional details, see explanation of [common indexing options](common-indexing-options.md).

## Retrieval

Topics and qrels are stored in [`src/main/resources/topics-and-qrels/`](../src/main/resources/topics-and-qrels/).
The regression experiments here evaluate on the 45 topics for which NIST has provided judgments as part of the TREC 2020 Deep Learning Track.
The original data can be found [here](https://trec.nist.gov/data/deep2020.html).

After indexing has completed, you should be able to perform retrieval as follows:

```
target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.msmarco-doc-segmented-unicoil-noexp/ \
  -topics src/main/resources/topics-and-qrels/topics.dl20.unicoil-noexp.0shot.tsv.gz \
  -topicreader TsvInt \
  -output runs/run.msmarco-doc-segmented-unicoil-noexp.unicoil.topics.dl20.unicoil-noexp.0shot.txt \
  -impact -pretokenized -hits 10000 -selectMaxPassage -selectMaxPassage.delimiter "#" -selectMaxPassage.hits 1000 &
```

Evaluation can be performed using `trec_eval`:

```
tools/eval/trec_eval.9.0.4/trec_eval -c -M 100 -m map src/main/resources/topics-and-qrels/qrels.dl20-doc.txt runs/run.msmarco-doc-segmented-unicoil-noexp.unicoil.topics.dl20.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -m ndcg_cut.10 src/main/resources/topics-and-qrels/qrels.dl20-doc.txt runs/run.msmarco-doc-segmented-unicoil-noexp.unicoil.topics.dl20.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -m recall.100 src/main/resources/topics-and-qrels/qrels.dl20-doc.txt runs/run.msmarco-doc-segmented-unicoil-noexp.unicoil.topics.dl20.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -m recall.1000 src/main/resources/topics-and-qrels/qrels.dl20-doc.txt runs/run.msmarco-doc-segmented-unicoil-noexp.unicoil.topics.dl20.unicoil-noexp.0shot.txt
```

## Effectiveness

With the above commands, you should be able to reproduce the following results:

| AP@100                                                                                                       | uniCOIL w/ doc2query-T5 expansion|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL20 (Doc)](https://trec.nist.gov/data/deep2020.html)                                                       | 0.3698    |


| nDCG@10                                                                                                      | uniCOIL w/ doc2query-T5 expansion|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL20 (Doc)](https://trec.nist.gov/data/deep2020.html)                                                       | 0.5893    |


| R@100                                                                                                        | uniCOIL w/ doc2query-T5 expansion|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL20 (Doc)](https://trec.nist.gov/data/deep2020.html)                                                       | 0.5872    |


| R@1000                                                                                                       | uniCOIL w/ doc2query-T5 expansion|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL20 (Doc)](https://trec.nist.gov/data/deep2020.html)                                                       | 0.7623    |

Note that in the official evaluation for document ranking, all runs were truncated to top-100 hits per query (whereas all top-1000 hits per query were retained for passage ranking).
Thus, average precision is computed to depth 100 (i.e., AP@100); nDCG@10 remains unaffected.
Remember that we keep qrels of _all_ relevance grades, unlike the case for passage ranking, where relevance grade 1 needs to be discarded when computing certain metrics.
Here, we retrieve 1000 hits per query, but measure AP at cutoff 100 (e.g., AP@100).
Thus, the experimental results reported here are directly comparable to the results reported in the [track overview paper](https://arxiv.org/abs/2102.07662).

## Reproduction Log[*](reproducibility.md)

To add to this reproduction log, modify [this template](../src/main/resources/docgen/templates/dl20-doc-segmented-unicoil-noexp.template) and run `bin/build.sh` to rebuild the documentation.

+ Results reproduced by [@manveertamber](https://github.com/manveertamber) on 2022-02-25 (commit [`7472d86`](https://github.com/castorini/anserini/commit/7472d862c7311bc8bbd30655c940d6396e27c223))
