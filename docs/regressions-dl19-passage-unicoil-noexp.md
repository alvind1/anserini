# Anserini Regressions: TREC 2019 Deep Learning Track (Passage)

**Model**: uniCOIL (without any expansions)

This page describes regression experiments, integrated into Anserini's regression testing framework, using uniCOIL (without any expansions) on the [TREC 2019 Deep Learning Track passage ranking task](https://trec.nist.gov/data/deep2019.html)..
The uniCOIL model is described in the following paper:

> Jimmy Lin and Xueguang Ma. [A Few Brief Notes on DeepImpact, COIL, and a Conceptual Framework for Information Retrieval Techniques.](https://arxiv.org/abs/2106.14807) _arXiv:2106.14807_.

The experiments on this page are not actually reported in the paper.
Here, a variant model without expansion is used.

Note that the NIST relevance judgments provide far more relevant passages per topic, unlike the "sparse" judgments provided by Microsoft (these are sometimes called "dense" judgments to emphasize this contrast).
For additional instructions on working with MS MARCO passage collection, refer to [this page](experiments-msmarco-passage.md).

The exact configurations for these regressions are stored in [this YAML file](../src/main/resources/regression/dl19-passage-unicoil-noexp.yaml).
Note that this page is automatically generated from [this template](../src/main/resources/docgen/templates/dl19-passage-unicoil-noexp.template) as part of Anserini's regression pipeline, so do not modify this page directly; modify the template instead and then run `bin/build.sh` to rebuild the documentation.

From one of our Waterloo servers (e.g., `orca`), the following command will perform the complete regression, end to end:

```
python src/main/python/run_regression.py --index --verify --search --regression dl19-passage-unicoil-noexp
```

## Corpus

We make available a version of the MS MARCO passage corpus that has already been processed with uniCOIL, i.e., gone through document expansion and term reweighting.
Thus, no neural inference is involved.
For details on how to train uniCOIL and perform inference, please see [this guide](https://github.com/luyug/COIL/tree/main/uniCOIL).

Download the corpus and unpack into `collections/`:

```
wget https://rgw.cs.uwaterloo.ca/JIMMYLIN-bucket0/data/msmarco-passage-unicoil.tar -P collections/

tar xvf collections/msmarco-passage-unicoil.tar -C collections/
```

To confirm, `msmarco-passage-unicoil.tar` is 3.3 GB and has MD5 checksum `78eef752c78c8691f7d61600ceed306f`.

With the corpus downloaded, the following command will perform the complete regression, end to end, on any machine:

```
python src/main/python/run_regression.py --index --verify --search --regression dl19-passage-unicoil-noexp \
  --corpus-path collections/msmarco-passage-unicoil-noexp
```

Alternatively, you can simply copy/paste from the commands below and obtain the same results.

## Indexing

Sample indexing command:

```
target/appassembler/bin/IndexCollection \
  -collection JsonVectorCollection \
  -input /path/to/msmarco-passage-unicoil-noexp \
  -index indexes/lucene-index.msmarco-passage-unicoil-noexp/ \
  -generator DefaultLuceneDocumentGenerator \
  -threads 16 -impact -pretokenized \
  >& logs/log.msmarco-passage-unicoil-noexp &
```

The path `/path/to/msmarco-passage-unicoil/` should point to the corpus downloaded above.

The important indexing options to note here are `-impact -pretokenized`: the first tells Anserini not to encode BM25 doclengths into Lucene's norms (which is the default) and the second option says not to apply any additional tokenization on the uniCOIL tokens.
Upon completion, we should have an index with 8,841,823 documents.

For additional details, see explanation of [common indexing options](common-indexing-options.md).

## Retrieval

Topics and qrels are stored in [`src/main/resources/topics-and-qrels/`](../src/main/resources/topics-and-qrels/).
The regression experiments here evaluate on the 43 topics for which NIST has provided judgments as part of the TREC 2019 Deep Learning Track.
The original data can be found [here](https://trec.nist.gov/data/deep2019.html).

After indexing has completed, you should be able to perform retrieval as follows:

```
target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.msmarco-passage-unicoil-noexp/ \
  -topics src/main/resources/topics-and-qrels/topics.dl19-passage.unicoil-noexp.0shot.tsv.gz \
  -topicreader TsvInt \
  -output runs/run.msmarco-passage-unicoil-noexp.unicoil.topics.dl19-passage.unicoil-noexp.0shot.txt \
  -impact -pretokenized &
```

Evaluation can be performed using `trec_eval`:

```
tools/eval/trec_eval.9.0.4/trec_eval -m map -c -l 2 src/main/resources/topics-and-qrels/qrels.dl19-passage.txt runs/run.msmarco-passage-unicoil-noexp.unicoil.topics.dl19-passage.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -m ndcg_cut.10 -c src/main/resources/topics-and-qrels/qrels.dl19-passage.txt runs/run.msmarco-passage-unicoil-noexp.unicoil.topics.dl19-passage.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -m recall.100 -c -l 2 src/main/resources/topics-and-qrels/qrels.dl19-passage.txt runs/run.msmarco-passage-unicoil-noexp.unicoil.topics.dl19-passage.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -m recall.1000 -c -l 2 src/main/resources/topics-and-qrels/qrels.dl19-passage.txt runs/run.msmarco-passage-unicoil-noexp.unicoil.topics.dl19-passage.unicoil-noexp.0shot.txt
```

## Effectiveness

With the above commands, you should be able to reproduce the following results:

| AP@1000                                                                                                      | uniCOIL (no expansions)|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL19 (Passage)](https://trec.nist.gov/data/deep2020.html)                                                   | 0.4033    |


| nDCG@10                                                                                                      | uniCOIL (no expansions)|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL19 (Passage)](https://trec.nist.gov/data/deep2020.html)                                                   | 0.6433    |


| R@100                                                                                                        | uniCOIL (no expansions)|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL19 (Passage)](https://trec.nist.gov/data/deep2020.html)                                                   | 0.5629    |


| R@1000                                                                                                       | uniCOIL (no expansions)|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [DL19 (Passage)](https://trec.nist.gov/data/deep2020.html)                                                   | 0.7752    |

Note that retrieval metrics are computed to depth 1000 hits per query (as opposed to 100 hits per query for document ranking).
Also, for computing nDCG, remember that we keep qrels of _all_ relevance grades, whereas for other metrics (e.g., AP), relevance grade 1 is considered not relevant (i.e., use the `-l 2` option in `trec_eval`).
The experimental results reported here are directly comparable to the results reported in the [track overview paper](https://arxiv.org/abs/2003.07820).

## Reproduction Log[*](reproducibility.md)

To add to this reproduction log, modify [this template](../src/main/resources/docgen/templates/dl19-passage-unicoil-noexp.template) and run `bin/build.sh` to rebuild the documentation.

+ Results reproduced by [@manveertamber](https://github.com/manveertamber) on 2022-02-25 (commit [`7472d86`](https://github.com/castorini/anserini/commit/7472d862c7311bc8bbd30655c940d6396e27c223))
+ Results reproduced by [@mayankanand007](https://github.com/mayankanand007) on 2022-02-28 (commit [`950d7fd`](https://github.com/castorini/anserini/commit/950d7fd88dbb87f39e9c1f6ccf9e41cbb6f04f36))
