//====================================================
// Start
//====================================================
const EventType = {
    UA_STATE: 'UA_STATE',
    USER_MEDIA_FAILED: 'USER_MEDIA_FAILED',
    INCOMING_CALL: 'INCOMING_CALL',
    PEER_CONNECTION: 'PEER_CONNECTION',
    ADD_STREAM: 'ADD_STREAM',
    ANSWERED: 'ANSWERED',
    CONFIRMED: 'CONFIRMED',
    CONNECTION: 'CONNECTION',
    ENDED: 'ENDED',
    FAILED: 'FAILED',
    CALLING: 'CALLING',
    AUDIO_MUTED: 'AUDIO_MUTED',
    AUDIO_UN_MUTED: 'AUDIO_UN_MUTED',
    VIDEO_MUTED: 'VIDEO_MUTED',
    VIDEO_UN_MUTED: 'VIDEO_UN_MUTED',
    HOLD: 'HOLD',
    UN_HOLD: 'UN_HOLD',
    IDLE: 'IDLE',
    GET_USER_MEDIA: 'GET_USER_MEDIA'
};

let inoutNumber;
let webrtcUser;
let webrtcPass;
let webrtcDomain;
let webrtcSocket;
let component;
let ua;

window.setProperty = function (user, pass, domain, socket, componentUI) {
    inoutNumber = user;
    webrtcUser = user;
    webrtcPass = pass;
    webrtcDomain = domain;
    webrtcSocket = socket;
    component = componentUI;
}
//====================================================

let session;			// Will contain the RTC Session

let constraints = {video: true, audio: true}; // Promise to send to peer

// ========================================
// Check if browser has audio/video support
// ========================================

const event = new CustomEvent('phone', {
    detail: {
        type: EventType.UA_STATE,
        phoneEvent: 'welcome',
    }
});

window.hasGetUserMedia = function () {
    constraints = {
        audio: 'DEFAULT_AUDIO_CONSTRAINTS',
        video: 'DEFAULT_VIDEO_CONSTRAINTS'
    }
    try {
        !!(window.navigator.mediaDevices && window.navigator.mediaDevices.getUserMedia(constraints));
    } catch (error) {
        event.detail.type = EventType.GET_USER_MEDIA;
        event.detail.phoneEvent = error;
        component.dispatchEvent(event);

    }
}

// ======================================
// Configure and activate your user agent
// ======================================
window.configureAndActivate = function () {

    let socket = new JsSIP.WebSocketInterface(webrtcSocket);
    let configuration = {
        sockets: [socket],
        uri: webrtcUser + "@" + webrtcDomain,
        password: webrtcPass,
        session_timers: false // If set to true, call will end after a minute or so
    };

    ua = new JsSIP.UA(configuration);
    ua.start();


// ==============================================
// If user agent successfully or was not successfully registrated
// ==============================================
    ua.on('connected', function (e) {
        event.detail.type = EventType.UA_STATE;
        event.detail.phoneEvent = 'connected';
        component.dispatchEvent(event);
    });

    ua.on('registered', function (e) {
        event.detail.type = EventType.UA_STATE;
        event.detail.phoneEvent = 'registered';
        component.dispatchEvent(event);
    });

    ua.on('registrationFailed', function (e) {
        event.detail.type = EventType.UA_STATE;
        event.detail.phoneEvent = 'registrationFailed';
        component.dispatchEvent(event);
    });

// ========================================
// Check for incoming and outgoing sessions
// ========================================
    ua.on('newRTCSession', function (e) {
        session = e.session;
        bindSessionEvents(session);
        if (e.originator === "remote") {
            if (session.remote_identity.uri.user === inoutNumber) {
                event.detail.type = EventType.CALLING;
                event.detail.phoneEvent = 'Calling..';
                component.dispatchEvent(event);
                answer();
            } else {
                event.detail.type = EventType.INCOMING_CALL;
                event.detail.phoneEvent = 'Incoming call from ' + session.remote_identity.uri.user;
                component.dispatchEvent(event);
            }
        } else if (e.originator === "local") {
            event.detail.type = EventType.CALLING;
            event.detail.phoneEvent = 'Calling..';
            component.dispatchEvent(event);
        }
    });
}

// ========
// SEND DTMF
// ========
window.callSendDTMF = function (symbol) {
    let dtmfTone = document.getElementById('dtmfTone');
    try {
        dtmfTone.play();
    } catch (e) {
    }
    let options = {
        'transportType': 'RFC2833'
    };
    session.sendDTMF(symbol, options);

}

// ========
// END CALL
// ========
window.endCall = function () {
    session.terminate();
}

// ========
// LOCAL UNHOLD\HOLD
// ========
window.localHold = function () {
    if (session.isOnHold().local === true) {
        session.unhold({
            'local': true,    // User has put the other peer on hold
            'remote': false   // Peer hasn't put user on hold
        });
    } else {
        session.hold({
            'local': true,    // User has put the other peer on hold
            'remote': false   // Peer hasn't put user on hold
        });
    }

}

// ========
// UNMUTE\MUTE AUDIO
// ========
window.muteAudio = function () {
    if (session.isMuted().audio === true) {
        session.unmute({
            audio: true,
            video: false
        });

    } else if (session.isMuted().audio === false) {
        session.mute({
            audio: true,
            video: false
        });
    }
}

// ========
// UNMUTE\MUTE VIDEO
// ========
window.muteVideo = function () {
    if (session.isMuted().video === true) {
        session.unmute({
            audio: false,
            video: true
        });

    } else if (session.isMuted().video === false) {
        session.mute({
            audio: false,
            video: true
        });
    }
}

// =====================
// Make an outgoing call
// =====================
window.makeCall = function (dialNum) {
    let options = {
        bindSessionEvents,
        constraints,
        'pcConfig': {
            'rtcpMuxPolicy': 'require',
            'iceServers': []
        }
    };

    session = ua.call('sip:' + dialNum + '@' + webrtcDomain, options);
}

// ===========
// ANSWER CALL
// ===========
window.answer = function () {
    let localStream = new MediaStream();
    let options = {
        localStream,
        constraints,
        'pcConfig': {
            'rtcpMuxPolicy': 'require',
            'iceServers': []
        }
    }
    session.answer(options);
}

// ===================================
// Bind events for the current session
// ===================================
window.bindSessionEvents = function (session) {

    session.on('getusermediafailed', function () {
        event.detail.type = EventType.USER_MEDIA_FAILED;
        event.detail.phoneEvent = 'No permission to access camera or microphone';
        component.dispatchEvent(event);
    });

    session.on('muted', function (e) {
        if (e.audio === true) {
            event.detail.type = EventType.AUDIO_MUTED;
            event.detail.phoneEvent = 'Microphone muted';
            component.dispatchEvent(event);
        } else if (e.video === true) {
            event.detail.type = EventType.VIDEO_MUTED;
            event.detail.phoneEvent = 'Camera muted';
            component.dispatchEvent(event);
        }
    });

    session.on('unmuted', function (e) {
        if (e.audio === true) {
            event.detail.type = EventType.AUDIO_UN_MUTED;
            event.detail.phoneEvent = 'Microphone unmuted';
            component.dispatchEvent(event);
        } else if (e.video === true) {
            event.detail.type = EventType.VIDEO_UN_MUTED;
            event.detail.phoneEvent = 'Camera unmuted';
            component.dispatchEvent(event);
        }
    });

    session.on('hold', function (e) {
        event.detail.type = EventType.HOLD;
        event.detail.phoneEvent = 'Local channel hold';
        component.dispatchEvent(event);
    });

    session.on('unhold', function (e) {
        event.detail.type = EventType.UN_HOLD;
        event.detail.phoneEvent = 'Local channel unhold';
        component.dispatchEvent(event);
    });


    session.on('confirmed', function (e) {
        if (session.remote_identity.uri.user !== inoutNumber) {
            event.detail.type = EventType.ANSWERED;
            event.detail.phoneEvent = 'Call answered';
            component.dispatchEvent(event);
        }
    });

    session.on('accepted', function (e) {

        stopRingBackTone();
        stopRingTone();

    });

    session.on('cancel', function (e) {

        stopRingBackTone();
        stopRingTone();

    });

    session.on('bye', function (e) {
        stopRingBackTone();
        stopRingTone();

    });

    session.on('failed', function (e) {
        stopRingBackTone();
        stopRingTone();

        event.detail.type = EventType.FAILED;
        event.detail.phoneEvent = 'Call failed';
        component.dispatchEvent(event);

        setTimeout(function () {
            event.detail.type = EventType.IDLE;
            event.detail.phoneEvent = 'idle';
            component.dispatchEvent(event);
        }, 5000);
    });

    session.on('rejected', function (e) {

        stopRingBackTone();
        stopRingTone();

    });

    session.on('ended', function (e) {
        event.detail.type = EventType.ENDED;
        event.detail.phoneEvent = 'Call is ended';
        component.dispatchEvent(event);

        setTimeout(function () {
            event.detail.type = EventType.IDLE;
            event.detail.phoneEvent = 'idle';
            component.dispatchEvent(event);
        }, 5000);
    });

    if (session.direction === "incoming") {
        startRingTone();
        session.on('peerconnection', function (e) {
            event.detail.type = EventType.PEER_CONNECTION;
            event.detail.phoneEvent = 'Call peer connection';
            component.dispatchEvent(event);

            e.peerconnection.ontrack = () => {

                let remoteStream = new MediaStream();
                e.peerconnection.getReceivers().forEach((receiver) => {
                    remoteStream.addTrack(receiver.track);
                });

                let remoteAudio = document.getElementById("localAudio");
                let remoteVideo = document.getElementById("localVideo");
                // Add the media stream to the audio element
                remoteAudio.srcObject = (e.stream);
                remoteVideo.srcObject = remoteStream;

            }
        });
    }
    if (session.direction === "outgoing") {
        startRingBackTone();

        session.connection.addEventListener('addstream', function (data) {
                event.detail.type = EventType.ADD_STREAM;
                event.detail.phoneEvent = 'Add stream';
                component.dispatchEvent(event);

                let remoteAudio = document.getElementById("localAudio");
                let remoteVideo = document.getElementById("localVideo");
                // Add the media stream to the audio element
                remoteAudio.srcObject = (data.stream);
                remoteVideo.srcObject = (data.stream);

            }
        )
        ;
    }
}

// Sound methods
window.startRingTone = function () {
    let ringTone = document.getElementById('ringTone');
    try {
        ringTone.play();
    } catch (e) {
    }
}

window.stopRingTone = function () {
    let ringTone = document.getElementById('ringTone');
    try {
        ringTone.pause();
    } catch (e) {
    }
}

window.startRingBackTone = function () {
    let ringBackTone = document.getElementById('ringBackTone');
    try {
        ringBackTone.play();
    } catch (e) {
    }
}

window.stopRingBackTone = function () {
    let ringBackTone = document.getElementById('ringBackTone');
    try {
        ringBackTone.pause();
    } catch (e) {
    }
}
