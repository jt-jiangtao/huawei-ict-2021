package com.cyj.whereareyou.service.Impl;

import com.cyj.whereareyou.service.CompareFaceService;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.frs.v2.FrsClient;
import com.huaweicloud.sdk.frs.v2.model.CompareFaceByUrlRequest;
import com.huaweicloud.sdk.frs.v2.model.CompareFaceByUrlResponse;
import com.huaweicloud.sdk.frs.v2.model.FaceCompareUrlReq;
import com.huaweicloud.sdk.frs.v2.region.FrsRegion;
import org.springframework.stereotype.Service;

/**
 * @Author: jiangtao
 * @Date: 2021/11/18 14:19
 */
@Service
public class CompareFaceServiceImpl implements CompareFaceService {

    @Override
    public double compareFaceByUrlSolution(String url1, String url2) {
        double similarity = 0;
        String ak = "QOKB0FXJLHTJPJDQWULO";
        String sk = "aQAfakyrpMhE8jXA4q4vhgn8a4yMQVNjHu530SeG";

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        FrsClient client = FrsClient.newBuilder()
                .withCredential(auth)
                .withRegion(FrsRegion.valueOf("cn-north-4"))
                .build();
        CompareFaceByUrlRequest request = new CompareFaceByUrlRequest();
        FaceCompareUrlReq body = new FaceCompareUrlReq();
        body.withImage2Url(url1);
        body.withImage1Url(url2);
        request.withBody(body);
        try {
            CompareFaceByUrlResponse response = client.compareFaceByUrl(request);
            similarity = response.getSimilarity();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }finally {
            return similarity;
        }
    }
}
