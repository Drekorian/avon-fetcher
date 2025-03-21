package cz.drekorian.avonfetcher.multiplatform.util.http

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

internal actual val engineConfig: HttpClientEngineFactory<HttpClientEngineConfig> = CIO