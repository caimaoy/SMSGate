package com.zx.sms.connect.manager.sgip;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.zx.sms.common.GlobalConstance;
import com.zx.sms.connect.manager.AbstractServerEndpointConnector;
import com.zx.sms.connect.manager.EndpointEntity;
import com.zx.sms.session.AbstractSessionStateManager;
import com.zx.sms.session.sgip.SgipSessionLoginManager;

public class SgipServerEndpointConnector extends AbstractServerEndpointConnector{

	public SgipServerEndpointConnector(EndpointEntity e) {
		super(e);
	}

	@Override
	protected void doBindHandler(ChannelPipeline pipe, EndpointEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doinitPipeLine(ChannelPipeline pipeline) {
		EndpointEntity entity = getEndpointEntity();
		pipeline.addLast(GlobalConstance.IdleCheckerHandlerName, new IdleStateHandler(0, 0, entity.getIdleTimeSec(), TimeUnit.SECONDS));
		pipeline.addLast("SgipServerIdleStateHandler", GlobalConstance.sgipidleHandler);
		pipeline.addLast(SgipCodecChannelInitializer.pipeName(), new SgipCodecChannelInitializer());
		pipeline.addLast("sessionLoginManager", new SgipSessionLoginManager(getEndpointEntity()));
		
	}

	@Override
	protected AbstractSessionStateManager createSessionManager(EndpointEntity entity, Map storeMap, boolean preSend) {
		// TODO Auto-generated method stub
		return null;
	}
}
