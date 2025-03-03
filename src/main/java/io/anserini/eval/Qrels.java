/*
 * Anserini: A Lucene toolkit for reproducible information retrieval research
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.anserini.eval;

import io.anserini.search.topicreader.TsvIntTopicReader;

public enum Qrels {
  TREC1_ADHOC("topics-and-qrels/qrels.adhoc.51-100.txt"),
  TREC2_ADHOC("topics-and-qrels/qrels.adhoc.101-150.txt"),
  TREC3_ADHOC("topics-and-qrels/qrels.adhoc.151-200.txt"),
  ROBUST04("topics-and-qrels/qrels.robust04.txt"),
  ROBUST05("topics-and-qrels/qrels.robust05.txt"),
  CORE17("topics-and-qrels/qrels.core17.txt"),
  CORE18("topics-and-qrels/qrels.core18.txt"),
  WT10G("topics-and-qrels/qrels.adhoc.451-550.txt"),
  TREC2004_TERABYTE("topics-and-qrels/qrels.terabyte04.701-750.txt"),
  TREC2005_TERABYTE("topics-and-qrels/qrels.terabyte05.751-800.txt"),
  TREC2006_TERABYTE("topics-and-qrels/qrels.terabyte06.801-850.txt"),
  TREC2011_WEB("topics-and-qrels/qrels.web.101-150.txt"),
  TREC2012_WEB("topics-and-qrels/qrels.web.151-200.txt"),
  TREC2013_WEB("topics-and-qrels/qrels.web.201-250.txt"),
  TREC2014_WEB("topics-and-qrels/qrels.web.251-300.txt"),
  MB11("topics-and-qrels/qrels.microblog2011.txt"),
  MB12("topics-and-qrels/qrels.microblog2012.txt"),
  MB13("topics-and-qrels/qrels.microblog2013.txt"),
  MB14("topics-and-qrels/qrels.microblog2014.txt"),
  CAR17V15_BENCHMARK_Y1_TEST("topics-and-qrels/qrels.car17v1.5.benchmarkY1test.txt"),
  CAR17V20_BENCHMARK_Y1_TEST("topics-and-qrels/qrels.car17v2.0.benchmarkY1test.txt"),
  TREC2019_DL_DOC("topics-and-qrels/qrels.dl19-doc.txt"),
  TREC2019_DL_PASSAGE("topics-and-qrels/qrels.dl19-passage.txt"),
  TREC2020_DL_DOC("topics-and-qrels/qrels.dl20-doc.txt"),
  TREC2020_DL_PASSAGE("topics-and-qrels/qrels.dl20-passage.txt"),
  TREC2021_DL_DOC("topics-and-qrels/qrels.dl21-doc.txt"),
  TREC2021_DL_PASSAGE("topics-and-qrels/qrels.dl21-passage.txt"),
  MSMARCO_DOC_DEV("topics-and-qrels/qrels.msmarco-doc.dev.txt"),
  MSMARCO_PASSAGE_DEV_SUBSET("topics-and-qrels/qrels.msmarco-passage.dev-subset.txt"),
  MSMARCO_V2_DOC_DEV("topics-and-qrels/qrels.msmarco-v2-doc.dev.txt"),
  MSMARCO_V2_DOC_DEV2("topics-and-qrels/qrels.msmarco-v2-doc.dev2.txt"),
  MSMARCO_V2_PASSAGE_DEV("topics-and-qrels/qrels.msmarco-v2-passage.dev.txt"),
  MSMARCO_V2_PASSAGE_DEV2("topics-and-qrels/qrels.msmarco-v2-passage.dev2.txt"),
  NTCIR8_ZH("topics-and-qrels/qrels.ntcir8.eval.txt"),
  CLEF2006_FR("topics-and-qrels/qrels.clef06fr.txt"),
  TREC2002_AR("topics-and-qrels/qrels.trec02ar.txt"),
  FIRE2012_BN("topics-and-qrels/qrels.fire12bn.176-225.txt"),
  FIRE2012_HI("topics-and-qrels/qrels.fire12hi.176-225.txt"),
  FIRE2012_EN("topics-and-qrels/qrels.fire12en.176-225.txt"),
  COVID_COMPLETE("topics-and-qrels/qrels.covid-complete.txt"),
  COVID_ROUND1("topics-and-qrels/qrels.covid-round1.txt"),
  COVID_ROUND2("topics-and-qrels/qrels.covid-round2.txt"),
  COVID_ROUND3("topics-and-qrels/qrels.covid-round3.txt"),
  COVID_ROUND3_CUMULATIVE("topics-and-qrels/qrels.covid-round3-cumulative.txt"),
  COVID_ROUND4("topics-and-qrels/qrels.covid-round4.txt"),
  COVID_ROUND4_CUMULATIVE("topics-and-qrels/qrels.covid-round4-cumulative.txt"),
  COVID_ROUND5("topics-and-qrels/qrels.covid-round5.txt"),
  TREC2018_BL("topics-and-qrels/qrels.backgroundlinking18.txt"),
  TREC2019_BL("topics-and-qrels/qrels.backgroundlinking19.txt"),
  TREC2020_BL("topics-and-qrels/qrels.backgroundlinking20.txt"),
  MRTYDI_V11_AR_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-ar.train.txt"),
  MRTYDI_V11_AR_DEV("topics-and-qrels/qrels.mrtydi-v1.1-ar.dev.txt"),
  MRTYDI_V11_AR_TEST("topics-and-qrels/qrels.mrtydi-v1.1-ar.test.txt"),
  MRTYDI_V11_BN_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-bn.train.txt"),
  MRTYDI_V11_BN_DEV("topics-and-qrels/qrels.mrtydi-v1.1-bn.dev.txt"),
  MRTYDI_V11_BN_TEST("topics-and-qrels/qrels.mrtydi-v1.1-bn.test.txt"),
  MRTYDI_V11_EN_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-en.train.txt"),
  MRTYDI_V11_EN_DEV("topics-and-qrels/qrels.mrtydi-v1.1-en.dev.txt"),
  MRTYDI_V11_EN_TEST("topics-and-qrels/qrels.mrtydi-v1.1-en.test.txt"),
  MRTYDI_V11_FI_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-fi.train.txt"),
  MRTYDI_V11_FI_DEV("topics-and-qrels/qrels.mrtydi-v1.1-fi.dev.txt"),
  MRTYDI_V11_FI_TEST("topics-and-qrels/qrels.mrtydi-v1.1-fi.test.txt"),
  MRTYDI_V11_ID_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-id.train.txt"),
  MRTYDI_V11_ID_DEV("topics-and-qrels/qrels.mrtydi-v1.1-id.dev.txt"),
  MRTYDI_V11_ID_TEST("topics-and-qrels/qrels.mrtydi-v1.1-id.test.txt"),
  MRTYDI_V11_JA_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-ja.train.txt"),
  MRTYDI_V11_JA_DEV("topics-and-qrels/qrels.mrtydi-v1.1-ja.dev.txt"),
  MRTYDI_V11_JA_TEST("topics-and-qrels/qrels.mrtydi-v1.1-ja.test.txt"),
  MRTYDI_V11_KO_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-ko.train.txt"),
  MRTYDI_V11_KO_DEV("topics-and-qrels/qrels.mrtydi-v1.1-ko.dev.txt"),
  MRTYDI_V11_KO_TEST("topics-and-qrels/qrels.mrtydi-v1.1-ko.test.txt"),
  MRTYDI_V11_RU_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-ru.train.txt"),
  MRTYDI_V11_RU_DEV("topics-and-qrels/qrels.mrtydi-v1.1-ru.dev.txt"),
  MRTYDI_V11_RU_TEST("topics-and-qrels/qrels.mrtydi-v1.1-ru.test.txt"),
  MRTYDI_V11_SW_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-sw.train.txt"),
  MRTYDI_V11_SW_DEV("topics-and-qrels/qrels.mrtydi-v1.1-sw.dev.txt"),
  MRTYDI_V11_SW_TEST("topics-and-qrels/qrels.mrtydi-v1.1-sw.test.txt"),
  MRTYDI_V11_TE_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-te.train.txt"),
  MRTYDI_V11_TE_DEV("topics-and-qrels/qrels.mrtydi-v1.1-te.dev.txt"),
  MRTYDI_V11_TE_TEST("topics-and-qrels/qrels.mrtydi-v1.1-te.test.txt"),
  MRTYDI_V11_TH_TRAIN("topics-and-qrels/qrels.mrtydi-v1.1-th.train.txt"),
  MRTYDI_V11_TH_DEV("topics-and-qrels/qrels.mrtydi-v1.1-th.dev.txt"),
  MRTYDI_V11_TH_TEST("topics-and-qrels/qrels.mrtydi-v1.1-th.test.txt"),
  BEIR_V1_0_0_ARGUANA_TEST("topics-and-qrels/qrels.beir-v1.0.0-arguana.test.txt"),
  BEIR_V1_0_0_CLIMATE_FEVER_TEST("topics-and-qrels/qrels.beir-v1.0.0-climate-fever.test.txt"),
  BEIR_V1_0_0_DBPEDIA_ENTITY_TEST("topics-and-qrels/qrels.beir-v1.0.0-dbpedia-entity.test.txt"),
  BEIR_V1_0_0_FEVER_TEST("topics-and-qrels/qrels.beir-v1.0.0-fever.test.txt"),
  BEIR_V1_0_0_FIQA_TEST("topics-and-qrels/qrels.beir-v1.0.0-fiqa.test.txt"),
  BEIR_V1_0_0_HOTPOTQA_TEST("topics-and-qrels/qrels.beir-v1.0.0-hotpotqa.test.txt"),
  BEIR_V1_0_0_NFCORPUS_TEST("topics-and-qrels/qrels.beir-v1.0.0-nfcorpus.test.txt"),
  BEIR_V1_0_0_NQ_TEST("topics-and-qrels/qrels.beir-v1.0.0-nq.test.txt"),
  BEIR_V1_0_0_QUORA_TEST("topics-and-qrels/qrels.beir-v1.0.0-quora.test.txt"),
  BEIR_V1_0_0_SCIDOCS_TEST("topics-and-qrels/qrels.beir-v1.0.0-scidocs.test.txt"),
  BEIR_V1_0_0_SCIFACT_TEST("topics-and-qrels/qrels.beir-v1.0.0-scifact.test.txt"),
  BEIR_V1_0_0_TREC_COVID_TEST("topics-and-qrels/qrels.beir-v1.0.0-trec-covid.test.txt"),
  BEIR_V1_0_0_WEBIS_TOUCHE2020_TEST("topics-and-qrels/qrels.beir-v1.0.0-webis-touche2020.test.txt");
  public final String path;

  Qrels(String path) {
    this.path = path;
  }
}
