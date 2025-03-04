# Anserini Regressions: MS MARCO (V2) Document Ranking

**Model**: uniCOIL (without any expansions) zero-shot on segmented documents (title/segment encoding)

This page describes regression experiments for document ranking _on the segmented version_ of the MS MARCO (V2) document corpus using the dev queries, which is integrated into Anserini's regression testing framework.
Here, we cover experiments with the uniCOIL model trained on the MS MARCO V1 passage ranking test collection, applied in a zero-shot manner, without any expansions.

The uniCOIL model is described in the following paper:

> Jimmy Lin and Xueguang Ma. [A Few Brief Notes on DeepImpact, COIL, and a Conceptual Framework for Information Retrieval Techniques.](https://arxiv.org/abs/2106.14807) _arXiv:2106.14807_.

**NOTE**: As an important detail, there is the question of what text we feed into the encoder to generate document representations.
Initially, we fed only the segment text, but later we realized that prepending the title of the document improves effectiveness.
This regression captures the latter title/segment encoding, which for clarity we call v2, distinguished from segment-only encoding, which is documented [here](regressions-msmarco-v2-doc-segmented-unicoil-noexp-0shot.md).
The segment-only encoding results are deprecated and kept around primarily for archival purposes and ablation experiments.
You probably don't want to use them.

The exact configurations for these regressions are stored in [this YAML file](../src/main/resources/regression/msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.yaml).
Note that this page is automatically generated from [this template](../src/main/resources/docgen/templates/msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.template) as part of Anserini's regression pipeline, so do not modify this page directly; modify the template instead.

From one of our Waterloo servers (e.g., `orca`), the following command will perform the complete regression, end to end:

```
python src/main/python/run_regression.py --index --verify --search --regression msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2
```

## Corpus

Download, unpack, and prepare the corpus:

```
# Download
wget https://rgw.cs.uwaterloo.ca/JIMMYLIN-bucket0/data/msmarco_v2_doc_segmented_unicoil_noexp_0shot_v2.tar -P collections/

# Unpack
tar -xvf collections/msmarco_v2_doc_segmented_unicoil_noexp_0shot_v2.tar -C collections/

# Rename (indexer is expecting corpus under a slightly different name)
mv collections/msmarco_v2_doc_segmented_unicoil_noexp_0shot_v2 collections/msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2
```

To confirm, `msmarco_v2_doc_segmented_unicoil_noexp_0shot_v2.tar` is 55 GB and has an MD5 checksum of `97ba262c497164de1054f357caea0c63`.

## Indexing

Sample indexing command:

```
target/appassembler/bin/IndexCollection \
  -collection JsonVectorCollection \
  -input /path/to/msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2 \
  -index indexes/lucene-index.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2/ \
  -generator DefaultLuceneDocumentGenerator \
  -threads 18 -impact -pretokenized \
  >& logs/log.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2 &
```

The path `/path/to/msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2/` should point to the corpus downloaded above.

The important indexing options to note here are `-impact -pretokenized`: the first tells Anserini not to encode BM25 doclengths into Lucene's norms (which is the default) and the second option says not to apply any additional tokenization on the uniCOIL tokens.
Upon completion, we should have an index with 124,131,404 documents.

For additional details, see explanation of [common indexing options](common-indexing-options.md).

## Retrieval

Topics and qrels are stored in [`src/main/resources/topics-and-qrels/`](../src/main/resources/topics-and-qrels/).
These regression experiments use the [dev queries](../src/main/resources/topics-and-qrels/topics.msmarco-v2-doc.dev.txt) and the [dev2 queries](../src/main/resources/topics-and-qrels/topics.msmarco-v2-doc.dev2.txt).

After indexing has completed, you should be able to perform retrieval as follows:

```
target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2/ \
  -topics src/main/resources/topics-and-qrels/topics.msmarco-v2-doc.dev.unicoil-noexp.0shot.tsv.gz \
  -topicreader TsvInt \
  -output runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev.unicoil-noexp.0shot.txt \
  -impact -pretokenized -hits 10000 -selectMaxPassage -selectMaxPassage.delimiter "#" -selectMaxPassage.hits 1000 &
target/appassembler/bin/SearchCollection \
  -index indexes/lucene-index.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2/ \
  -topics src/main/resources/topics-and-qrels/topics.msmarco-v2-doc.dev2.unicoil-noexp.0shot.tsv.gz \
  -topicreader TsvInt \
  -output runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev2.unicoil-noexp.0shot.txt \
  -impact -pretokenized -hits 10000 -selectMaxPassage -selectMaxPassage.delimiter "#" -selectMaxPassage.hits 1000 &
```

Evaluation can be performed using `trec_eval`:

```
tools/eval/trec_eval.9.0.4/trec_eval -c -m recall.100 src/main/resources/topics-and-qrels/qrels.msmarco-v2-doc.dev.txt runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -m recall.1000 src/main/resources/topics-and-qrels/qrels.msmarco-v2-doc.dev.txt runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -M 100 -m map -c -M 100 -m recip_rank src/main/resources/topics-and-qrels/qrels.msmarco-v2-doc.dev.txt runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -m recall.100 src/main/resources/topics-and-qrels/qrels.msmarco-v2-doc.dev2.txt runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev2.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -m recall.1000 src/main/resources/topics-and-qrels/qrels.msmarco-v2-doc.dev2.txt runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev2.unicoil-noexp.0shot.txt
tools/eval/trec_eval.9.0.4/trec_eval -c -M 100 -m map -c -M 100 -m recip_rank src/main/resources/topics-and-qrels/qrels.msmarco-v2-doc.dev2.txt runs/run.msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.unicoil-noexp-0shot.topics.msmarco-v2-doc.dev2.unicoil-noexp.0shot.txt
```

## Effectiveness

With the above commands, you should be able to reproduce the following results:

| MAP@100                                                                                                      | uniCOIL (noexp) zero-shot|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [MS MARCO V2 Doc: Dev](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                          | 0.2205    |
| [MS MARCO V2 Doc: Dev2](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                         | 0.2291    |


| MRR@100                                                                                                      | uniCOIL (noexp) zero-shot|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [MS MARCO V2 Doc: Dev](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                          | 0.2231    |
| [MS MARCO V2 Doc: Dev2](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                         | 0.2314    |


| R@100                                                                                                        | uniCOIL (noexp) zero-shot|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [MS MARCO V2 Doc: Dev](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                          | 0.7460    |
| [MS MARCO V2 Doc: Dev2](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                         | 0.7498    |


| R@1000                                                                                                       | uniCOIL (noexp) zero-shot|
|:-------------------------------------------------------------------------------------------------------------|-----------|
| [MS MARCO V2 Doc: Dev](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                          | 0.8987    |
| [MS MARCO V2 Doc: Dev2](https://microsoft.github.io/msmarco/TREC-Deep-Learning.html)                         | 0.8995    |

## Reproduction Log[*](reproducibility.md)

To add to this reproduction log, modify [this template](../src/main/resources/docgen/templates/msmarco-v2-doc-segmented-unicoil-noexp-0shot-v2.template) and run `bin/build.sh` to rebuild the documentation.
