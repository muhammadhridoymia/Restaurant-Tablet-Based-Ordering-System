package com.example.order.components

import BannerViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.order.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerImg() {

    val viewModel: BannerViewModel = viewModel()
    val banners by viewModel.Bannerdata
    val loading by viewModel.loading

    // Pager state
    val pagerState = rememberPagerState(
        pageCount = { banners.size }
    )

    // Fetch banners once
    LaunchedEffect(Unit) {
        viewModel.fetchBanners()
    }

    // Auto scroll banner
    LaunchedEffect(pagerState.currentPage) {
        if (banners.isNotEmpty()) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % banners.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {

        if (loading) {
            CircularProgressIndicator()
        }
        else if (banners.isEmpty()) {
            // Optional empty state
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = "Restaurant Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(150.dp, 190.dp)
                    .clip(RoundedCornerShape(18.dp)),
                contentScale = ContentScale.Crop
            )
        }
        else {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
            ) { page ->

                AsyncImage(
                    model = banners[page].img,
                    contentDescription = "Banner Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
