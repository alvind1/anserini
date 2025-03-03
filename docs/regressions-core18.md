# Anserini Regressions: TREC 2018 Common Core Track

**Models**: various bag-of-words approaches

This page describes regressions for the TREC 2018 Common Core Track, which uses the [TREC Washington Post Corpus](https://trec.nist.gov/data/wapost/).
The exact configurations for these regressions are stored in [this YAML file](../src/main/resources/regression/core18.yaml).
Note that this page is automatically generated from [this template](../src/main/resources/docgen/templates/core18.template) as part of Anserini's regression pipeline, so do not modify this page directly; modify the template instead.

From one of our Waterloo servers (e.g., `orca`), the following command will perform the complete regression, end to end:

```
python src/main/python/run_regression.py --index --verify --search --regression core18
```

## Indexing

Typical indexing command:

```
target/appassembler/bin/IndexCollection \
  -collection WashingtonPostCollection \
  -input /path/to/wapo.v2 \
  -index indexes/lucene-index.wapo.v2/ \
  -generator WashingtonPostGenerator \
  -threads 1 -storePositions -storeDocvectors -storeRaw \
  >& logs/log.wapo.v2 &
```

The directory `/path/to/core18/` should be the root directory of the [TREC Washington Post Corpus](https://trec.nist.gov/data/wapost/), i.e., `ls /path/to/core18/`
should bring up a single JSON file.

For additional details, see explanation of [common indexing options](common-indexing-options.md).

## Retrieval

Topics and qrels are stored in [`src/main/resources/topics-and-qrels/`](../src/main/resources/topics-and-qrels/), downloaded from NIST:

+ [`topics.core18.txt`](../src/main/resources/topics-and-qrels/topics.core18.txt): [topics for the TREC 2018 Common Core Track](https://trec.nist.gov/data/core/topics2018.txt)
+ [`qrels.core18.txt`](../src/main/resources/topics-and-qrels/qrels.core18.txt): [qrels for the TREC 2018 Common Core Track](https://trec.nist.gov/data/core/qrels2018.txt)

After indexing has completed, you should be able to perform retrieval as follows:

```
target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.wapo.v2/ \
  -topics src/main/resources/topics-and-qrels/topics.core18.txt \
  -topicreader Trec \
  -output runs/run.wapo.v2.bm25.topics.core18.txt \
  -bm25 &

target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.wapo.v2/ \
  -topics src/main/resources/topics-and-qrels/topics.core18.txt \
  -topicreader Trec \
  -output runs/run.wapo.v2.bm25+rm3.topics.core18.txt \
  -bm25 -rm3 &

target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.wapo.v2/ \
  -topics src/main/resources/topics-and-qrels/topics.core18.txt \
  -topicreader Trec \
  -output runs/run.wapo.v2.bm25+ax.topics.core18.txt \
  -bm25 -axiom -axiom.deterministic -rerankCutoff 20 &

target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.wapo.v2/ \
  -topics src/main/resources/topics-and-qrels/topics.core18.txt \
  -topicreader Trec \
  -output runs/run.wapo.v2.ql.topics.core18.txt \
  -qld &

target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.wapo.v2/ \
  -topics src/main/resources/topics-and-qrels/topics.core18.txt \
  -topicreader Trec \
  -output runs/run.wapo.v2.ql+rm3.topics.core18.txt \
  -qld -rm3 &

target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.wapo.v2/ \
  -topics src/main/resources/topics-and-qrels/topics.core18.txt \
  -topicreader Trec \
  -output runs/run.wapo.v2.ql+ax.topics.core18.txt \
  -qld -axiom -axiom.deterministic -rerankCutoff 20 &
```

Evaluation can be performed using `trec_eval`:

```
tools/eval/trec_eval.9.0.4/trec_eval -m map -m P.30 src/main/resources/topics-and-qrels/qrels.core18.txt runs/run.wapo.v2.bm25.topics.core18.txt

tools/eval/trec_eval.9.0.4/trec_eval -m map -m P.30 src/main/resources/topics-and-qrels/qrels.core18.txt runs/run.wapo.v2.bm25+rm3.topics.core18.txt

tools/eval/trec_eval.9.0.4/trec_eval -m map -m P.30 src/main/resources/topics-and-qrels/qrels.core18.txt runs/run.wapo.v2.bm25+ax.topics.core18.txt

tools/eval/trec_eval.9.0.4/trec_eval -m map -m P.30 src/main/resources/topics-and-qrels/qrels.core18.txt runs/run.wapo.v2.ql.topics.core18.txt

tools/eval/trec_eval.9.0.4/trec_eval -m map -m P.30 src/main/resources/topics-and-qrels/qrels.core18.txt runs/run.wapo.v2.ql+rm3.topics.core18.txt

tools/eval/trec_eval.9.0.4/trec_eval -m map -m P.30 src/main/resources/topics-and-qrels/qrels.core18.txt runs/run.wapo.v2.ql+ax.topics.core18.txt
```

## Effectiveness

With the above commands, you should be able to reproduce the following results:

| MAP                                                                                                          | BM25      | +RM3      | +Ax       | QL        | +RM3      | +Ax       |
|:-------------------------------------------------------------------------------------------------------------|-----------|-----------|-----------|-----------|-----------|-----------|
| [TREC 2018 Common Core Track Topics](../src/main/resources/topics-and-qrels/topics.core18.txt)               | 0.2496    | 0.3139    | 0.2840    | 0.2527    | 0.3074    | 0.2920    |


| P30                                                                                                          | BM25      | +RM3      | +Ax       | QL        | +RM3      | +Ax       |
|:-------------------------------------------------------------------------------------------------------------|-----------|-----------|-----------|-----------|-----------|-----------|
| [TREC 2018 Common Core Track Topics](../src/main/resources/topics-and-qrels/topics.core18.txt)               | 0.3573    | 0.4200    | 0.3947    | 0.3653    | 0.3993    | 0.4013    |

## Reproduction Log[*](reproducibility.md)

+ Results reproduced by [@andrewyates](https://github.com/andrewyates) on 2018-11-30 (commit [`c1aac5`](https://github.com/castorini/Anserini/commit/c1aac5e353e2ab77db3e7106cb4c017a09ce0fe9))
+ Results reproduced by [@chriskamphuis](https://github.com/chriskamphuis) on 2019-09-07 (commit [`61f6f20`](https://github.com/castorini/anserini/commit/61f6f20ff6872484966ea1badcdcdcebf1eea852))
+ Results reproduced by [@nikhilro](https://github.com/nikhilro) on 2020-01-26 (commit [`d5ee069`](https://github.com/castorini/anserini/commit/d5ee069399e6a306d7685bda756c1f19db721156))
+ Results reproduced by [@edwinzhng](https://github.com/edwinzhng) on 2020-01-26 (commit [`7b76dfb`](https://github.com/castorini/anserini/commit/7b76dfbea7e0c01a3a5dc13e74f54852c780ec9b))
+ Results reproduced by [@yuki617](https://github.com/yuki617) on 2020-05-17 (commit [`cee4463`](https://github.com/castorini/anserini/commit/cee446338137415899436f0b2f2d738769745cde))
+ Results reproduced by [@x65han](https://github.com/x65han) on 2020-05-19 (commit [`33b0684`](https://github.com/castorini/anserini/commit/33b068437c4582067486e5fe79dfbecb8d4a145c))
+ Results reproduced by [@yxzhu16](https://github.com/yxzhu16) on 2020-07-17 (commit [`fad12be`](https://github.com/castorini/anserini/commit/fad12be2e37a075100707c3a674eb67bc0aa57ef))
