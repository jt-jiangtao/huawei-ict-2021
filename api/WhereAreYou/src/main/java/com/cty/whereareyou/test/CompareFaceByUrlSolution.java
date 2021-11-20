package com.cty.whereareyou.test;

import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.frs.v2.region.FrsRegion;
import com.huaweicloud.sdk.frs.v2.*;
import com.huaweicloud.sdk.frs.v2.model.*;


public class CompareFaceByUrlSolution {

    public static void main(String[] args) {
//        String ak = System.getenv("HWCLOUD_AK");
//        String sk = System.getenv("HWCLOUD_SK");
//        String securityToken = System.getenv("HWCLOUD_SECURITY_TOKEN"); // 在临时aksk场景下使用

        String ak = "QOKB0FXJLHTJPJDQWULO";
        String sk = "aQAfakyrpMhE8jXA4q4vhgn8a4yMQVNjHu530SeG";
//        String securityToken = "";

        ICredential auth = new BasicCredentials()
//                .withSecurityToken(securityToken) // 在临时aksk场景下使用
                .withAk(ak)
                .withSk(sk);

        FrsClient client = FrsClient.newBuilder()
                .withCredential(auth)
                .withRegion(FrsRegion.valueOf("cn-north-4"))
                .build();
        CompareFaceByUrlRequest request = new CompareFaceByUrlRequest();
        FaceCompareUrlReq body = new FaceCompareUrlReq();
        body.withImage2Url("https://image.jiangtao.website/t/2.png");
        body.withImage1Url("https://image.jiangtao.website/t/2.png");
        request.withBody(body);
        try {
            CompareFaceByUrlResponse response = client.compareFaceByUrl(request);
            System.out.println(response.toString());
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
    }
}
