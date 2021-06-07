package com.awse.commerce.domains.member.service;

import com.awse.commerce.domains.member.dto.MemberDto;
import com.awse.commerce.domains.member.dto.OAuth2Attribute;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.info(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                            .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attribute attributes = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);
        log.info("saveOrUpdate..");
        log.info(member);

        MemberDto oAuth2Member = new MemberDto(member, attributes.getAttributes());

        return oAuth2Member;

    }

    private Member saveOrUpdate(OAuth2Attribute attribute) {
        String oAuth2Email = attribute.getEmail();

        Optional<Member> member = memberRepository.findByEmail(oAuth2Email);

        if(!member.isPresent()) {
            return createDefaultMember(attribute);
        }

        return member.get();
    }

    private Member createDefaultMember(OAuth2Attribute attribute) {
       Member member = Member.builder()
                .name(attribute.getName())
                .email(attribute.getEmail())
                .password("")
                .phone("없음")
                .address(new Address("", "", ""))
                .role(MemberRole.USER)
                .build();

       return memberRepository.save(member);
    }

}
